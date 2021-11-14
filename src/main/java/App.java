import Exceptions.APIException;
import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oUserDao;
import models.Department;
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