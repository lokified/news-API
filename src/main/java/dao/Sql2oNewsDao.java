package dao;

import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oNewsDao {

    private final Sql2o sql2o;

    public Sql2oNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

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

    public List<News> getAll(){
        try (Connection conn = sql2o.open()){
          String sql = "SELECT * FROM news";
          return conn.createQuery(sql)
                  .executeAndFetch(News.class);
        }
    }

    public News findById(int id) {
         try (Connection conn = sql2o.open()){
          String sql = "SELECT * FROM news WHERE id = :id";
             return conn.createQuery(sql)
                     .addParameter("id",id)
                     .executeAndFetchFirst(News.class);
        }
    }

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

    public void clearAll() {
        String sql = "DELETE from news";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}