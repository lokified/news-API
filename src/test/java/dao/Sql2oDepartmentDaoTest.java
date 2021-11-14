package dao;

import models.Department;
import models.News;
import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentDaoTest {

    private static Connection conn;
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oUserDao userDao;
    private static Sql2oNewsDao newsDao;

    @BeforeClass
    public static void setUp() {
        String connectionString ="jdbc:postgresql://localhost:5432/mynews_test";
        Sql2o sql2o = new Sql2o(connectionString,"moringa","dammey5");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
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
    public void findById_returnsCorrectDepartment() {
        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        Department otherDepartment = new Department("Kitchen dpt","deals with food");
        departmentDao.add(otherDepartment);
        Department foundDepartment = departmentDao.findById(testDepartment.getId());
        Department foundAnotherDepartment = departmentDao.findById(otherDepartment.getId());
        assertEquals("Human Resource",foundDepartment.getName());
        assertEquals("Kitchen dpt",foundAnotherDepartment.getName());
    }

    @Test
    public void update_updatesNameDescriptionCorrectly() {
        String initialName = "Human Resource";
        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        departmentDao.update(testDepartment.getId(),"Security","protects all people");
        Department updatedDepartment = departmentDao.findById(testDepartment.getId());
        assertNotEquals(initialName,updatedDepartment.getName());
    }

    @Test
    public void deleteById_deletesCorrectly() {
        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        Department otherDepartment = new Department("Kitchen dpt","deals with food");
        departmentDao.add(otherDepartment);
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(1,departmentDao.getAll().size());
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

    @Test
    public void DepartmentsReturnsNewsCorrectly() throws Exception {
        News testNews = setUpNews();
        newsDao.add(testNews);


        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);
        newsDao.addNewsToDepartment(testNews,testDepartment);

        News[] news = {testNews};

        assertEquals(Arrays.asList(news), departmentDao.getAllNewsForADepartment(testDepartment.getId()));
    }

    //helpers
    public Department setUpDepartment() {
        return new Department("Human Resource","caters for employees needs");
    }
    public News setUpNews() {
        return new News("Renaming","Our department will be renamed");
    }
}