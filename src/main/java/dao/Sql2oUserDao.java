package dao;

import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oUserDao {

    private final Sql2o sql2o;

    public Sql2oUserDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    public void add(User user) {
        String sql = "INSERT INTO users (name,gender,positions,role,departmentId) VALUES (:name, :gender, :positions, :role, :departmentId)";
        try (Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql,true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public List<User> getAll(){
        try(Connection conn = sql2o.open()) {
            String sql = "SELECT * FROM users";
            return conn.createQuery(sql)
                    .executeAndFetch(User.class);
        }

    }

    public void clearAll() {
        String sql = "DELETE from users";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}