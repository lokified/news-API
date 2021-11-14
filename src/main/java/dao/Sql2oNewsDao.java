package dao;

import models.Department;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oNewsDao implements NewsDao {

    private final Sql2o sql2o;

    public Sql2oNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(News news) {
        String sql = "INSERT INTO news (title,content) VALUES (:title, :content)";
        try (Connection conn = sql2o.open()) {
            int id = (int) conn.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<News> getAll(){
        try (Connection conn = sql2o.open()){
          String sql = "SELECT * FROM news";
          return conn.createQuery(sql)
                  .executeAndFetch(News.class);
        }
    }

    @Override
    public News findById(int id) {
         try (Connection conn = sql2o.open()){
          String sql = "SELECT * FROM news WHERE id = :id";
             return conn.createQuery(sql)
                     .addParameter("id",id)
                     .executeAndFetchFirst(News.class);
        }
    }

    @Override
    public void update(int id,String title, String content) {
        try (Connection conn = sql2o.open()){
           String sql = "UPDATE news SET (title,content) = (:title,:content) WHERE id = :id";
           conn.createQuery(sql)
                   .addParameter("title", title)
                   .addParameter("content", content)
                   .addParameter("id", id)
                   .executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) {
        try (Connection conn = sql2o.open()){
            String sql = "DELETE FROM news WHERE id = :id";
             conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addNewsToDepartment(News news, Department department) {
        String sql = "INSERT INTO departments_news (departmentId, newsId) VALUES (:departmentId, :newsId)";
        try(Connection conn = sql2o.open()) {
          conn.createQuery(sql)
                  .addParameter("departmentId",department.getId())
                  .addParameter("newsId",news.getId())
                  .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAllDepartmentsForNews(int id) {
        List<Department> departments = new ArrayList<>();

        String joinQuery = "SELECT departmentId FROM departments_news WHERE newsId = :newsId";

        try( Connection conn = sql2o.open()) {
            List<Integer> allDepartmentIds = conn.createQuery(joinQuery)
                    .addParameter("newsId",id)
                    .executeAndFetch(Integer.class);

            for (Integer departmentId : allDepartmentIds) {
                String departmentQuery = "SELECT * FROM departments WHERE id = :departmentId";
                departments.add(
                        conn.createQuery(departmentQuery)
                                .addParameter("departmentId", departmentId)
                                .executeAndFetchFirst(Department.class)
                );
            }
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from news";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}