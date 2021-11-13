package dao;

import models.Department;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {

    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;

    @BeforeClass
    public static void setUp() {
        String connectionString ="jdbc:postgresql://localhost:5432/mynews_test";
        Sql2o sql2o = new Sql2o(connectionString,"moringa","dammey5");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() {
        System.out.println("clearing databases");
        departmentDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add_addsDepartmentsToDB() {
        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        assertEquals(1,departmentDao.getAll().size());
    }

    @Test
    public void getAll_returnsAllDepartments() {
        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        Department otherDepartment = new Department("Kitchen dpt","deals with food");
        departmentDao.add(otherDepartment);
        assertEquals(2,departmentDao.getAll().size());
    }

    @Test
    public void clearAll_clearsAllDepartments() {
        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        Department otherDepartment = new Department("Kitchen dpt","deals with food");
        departmentDao.add(otherDepartment);
        departmentDao.clearAll();
        assertEquals(0,departmentDao.getAll().size());
    }

    //helpers
    public Department setUpDepartment() {
        return new Department("Human Resource","caters for employees needs");
    }
}