package dao;

import models.Department;
import models.News;

import java.util.List;

public interface NewsDao {

    //create
    void add(News news);
    void addNewsToDepartment(News news, Department department);

    //read
    List<News> getAll();
    List<Department> getAllDepartmentsForNews(int id);
    News findById (int id);

    //update
    void update(int id, String name,String description);

    //delete
    void deleteById(int id);
    void clearAll();
}