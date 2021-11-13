package dao;

import models.Department;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oUserDao implements UserDao {

    private final Sql2o sql2o;

    public Sql2oUserDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
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

    @Override
    public List<User> getAll(){
        try(Connection conn = sql2o.open()) {
            String sql = "SELECT * FROM users";
            return conn.createQuery(sql)
                    .executeAndFetch(User.class);
        }

    }

    @Override
    public List<User> getDepartmentForAUser(int departmentId) {
        try(Connection conn = sql2o.open()) {
            return conn.createQuery("SELECT * FROM users WHERE departmentId = :departmentId")
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public User findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }

    @Override
    public void update(int id,String name, String gender, String positions,String role,int departmentId) {
        String sql = "UPDATE users SET (name,gender,positions,role,departmentId) = (:name, :gender, :positions, :role, :departmentId) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", name)
                    .addParameter("gender", gender)
                    .addParameter("positions", positions)
                    .addParameter("role", role)
                    .addParameter("departmentId",departmentId)
                    .addParameter("id",id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users WHERE id = :id";
        try(Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from users";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}