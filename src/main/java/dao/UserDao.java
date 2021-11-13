package dao;

import models.Department;
import models.User;

import java.util.List;

public interface UserDao {

    //create
    public void add(User user);

    public void addUserToDepartment(User user, Department department);

    //read
    List<User> getAll();
    List<Department> getDepartmentForAUser(int departmentId);
    User findById (int id);

    //update
    void update(int id, String name, String gender, String positions,int departmentId);

    //delete
    void deleteById(int id);
    void clearAll();
}
