package dao;

import models.Department;
import models.News;

import java.util.List;

public interface NewsDao {

    //create
    public void add(News news);

    //read
    List<News> getAll();
    News findById (int id);

    //update
    void update(int id, String name,String description);

    //delete
    void deleteById(int id);
    void clearAll();
}