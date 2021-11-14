import Exceptions.APIException;
import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oUserDao;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App{
    public static void main(String[] args) {

        staticFileLocation("/public");


        String connectionString ="jdbc:postgresql://localhost:5432/mynews";
        Sql2o sql2o = new Sql2o(connectionString,"moringa","dammey5");
        Sql2oUserDao userDao = new Sql2oUserDao(sql2o);
        Connection conn = sql2o.open();
        Gson gson = new Gson();

        //create users
        post("/users/new","application/json", (req,res) -> {
            User user = gson.fromJson(req.body(),User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        exception(APIException.class, (exception, req, res) -> {
            APIException err = (APIException) exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        //FILTERS
        after((req, res) -> {
            res.type("application/json");
        });
    }
}