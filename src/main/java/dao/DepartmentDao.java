package dao;

import models.Department;
import models.User;

import java.util.List;

public interface DepartmentDao {

    //create
    public void add(Department department);

    //read
    List<Department> getAll();
    Department findById (int id);

    //update
    void update(int id, String name,String description);

    //delete
    void deleteById(int id);
    void clearAll();
}