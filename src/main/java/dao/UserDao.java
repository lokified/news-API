package dao;

import models.Department;
import models.User;

import java.util.List;

public interface UserDao {

    //create
    public void add(User user);

    //read
    List<User> getAll();
    List<User> getDepartmentForAUser(int departmentId);
    User findById (int id);

    //update
    void update( String name, String gender, String positions, String role,int departmentId);

    //delete
    void deleteById(int id);
    void clearAll();
}
