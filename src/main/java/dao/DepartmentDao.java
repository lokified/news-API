package dao;

import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface DepartmentDao {

    //create
    public void add(Department department);

    //read
    List<Department> getAll();
    List<News> getAllNewsForADepartment(int departmentId);
    Department findById (int id);

    //update
    void update(int id, String name,String description, int employees);

    //delete
    void deleteById(int id);
    void clearAll();
}