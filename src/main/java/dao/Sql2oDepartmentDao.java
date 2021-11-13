package dao;

import models.Department;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oDepartmentDao {

    private final Sql2o sql2o;

    public Sql2oDepartmentDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void add(Department department){
        String sql = "INSERT INTO departments (name,description) VALUES (:name, :description)";
        try(Connection conn = sql2o.open()) {
            int id = (int) conn.createQuery(sql,true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    public List<Department> getAll() {
        try (Connection conn = sql2o.open()){
            String sql = "SELECT * FROM departments";
         return conn.createQuery(sql)
                    .executeAndFetch(Department.class);
        }
    }

    public List<User> getDepartmentUsers(Department department) {
        try (Connection conn = sql2o.open()){
            String sql = "SELECT FROM users WHERE departmentId = :id";
            return conn.createQuery(sql)
                    .addParameter("id",department.getId())
                    .executeAndFetch(User.class);
        }
    }

    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

}