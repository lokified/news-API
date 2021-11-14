import Exceptions.APIException;
import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App{
    public static void main(String[] args) {

        staticFileLocation("/public");


        String connectionString ="jdbc:postgresql://localhost:5432/mynews";
        Sql2o sql2o = new Sql2o(connectionString,"moringa","dammey5");
        Sql2oUserDao userDao = new Sql2oUserDao(sql2o);
        Sql2oDepartmentDao departmentDao = new Sql2oDepartmentDao(sql2o);
        Sql2oNewsDao newsDao = new Sql2oNewsDao(sql2o);
        Connection conn = sql2o.open();
        Gson gson = new Gson();

        //create users
        post("/users/new","application/json", (req,res) -> {
            User user = gson.fromJson(req.body(),User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //create department
        post("/departments/new","application/json",(req,res)->{
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            res.type("application/json");
            return gson.toJson(department);
        });

        //create news
        post("/news/new","application/json",(req,res)->{
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            res.type("application/json");
            return gson.toJson(news);
        });

        //posts news to a department
        post("/departments/:departmentId/news/:newsId","application/json",(req,res)->{
            int departmentId = Integer.parseInt(req.params("departmentId"));
            int newsId = Integer.parseInt(req.params("newsId"));

            Department department = departmentDao.findById(departmentId);
            News news = newsDao.findById(newsId);

            if (department != null && news != null) {
                newsDao.addNewsToDepartment(news,department);

                res.status(201);
                return gson.toJson(String.format("Department '%s' and News '%s' have been associated",news.getTitle(), department.getName()));
            }else {
                throw new APIException(404, String.format("Department or News does not exist"));
            }


        });

        //show departments news
        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new APIException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentDao.getAllNewsForADepartment(departmentId).size() == 0){
                return "{\"message\":\"I'm sorry, but no news are listed for this department.\"}";
            }
            else {
                return gson.toJson(departmentDao.getAllNewsForADepartment(departmentId));
            }
        });

        //get users
        get("/users","application/json",(req,res) ->{
            if(userDao.getAll().size() > 0) {
                return gson.toJson(userDao.getAll());
            }
            else {
                return "no users in the database";
            }
        });

        //find individual users
        get("/users/:id","application/json",(req,res) ->{
            int userId = Integer.parseInt(req.params("id"));
            User foundUser = userDao.findById(userId);

            if(foundUser == null) {
                throw new  APIException(404,String.format("No user wih the id: %s exists",req.params("id")));
            }
            return gson.toJson(foundUser);
        });

        //show users of a department
        get("/users/:id/department","application/json",(req,res)-> {
            int userId = Integer.parseInt(req.params("id"));
            List<User> userDepartment = userDao.getDepartmentForAUser(userId);

            if(userDepartment.size() == 0) {
                throw new  APIException(404,"no departments found");
            }
            return gson.toJson(userDepartment);
        });

        //get all departments
        get("/departments","application/json",(req,res) ->{
            if(departmentDao.getAll().size() > 0) {
                return gson.toJson(departmentDao.getAll());
            }
            else {
                return "no departments in the database";
            }
        });

        //find departments
        get("/departments/:id", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);

            if (departmentToFind == null){
                throw new APIException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(departmentToFind);
        });

        //show all news
        get("/news","application/json",(req,res) ->{
            if(newsDao.getAll().size() > 0) {
                return gson.toJson(newsDao.getAll());
            }
            else {
                return "no news in the database";
            }
        });

        //show individual news
        get("/news/:id","application/json",(req,res) ->{
            int newsId = Integer.parseInt(req.params("id"));
            News foundNews = newsDao.findById(newsId);

            if(foundNews == null) {
                throw new  APIException(404,String.format("No news wih the id: %s exists",req.params("id")));
            }
            return gson.toJson(foundNews);
        });

        //FILTERS
        exception(APIException.class, (exception, req, res) -> {
            APIException err = (APIException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) -> {
            res.type("application/json");
        });
    }
}