package dao;

import models.Department;
import models.News;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {

    private static Connection conn;
    private static Sql2oNewsDao newsDao;
    private static Sql2oDepartmentDao departmentDao;

    @BeforeClass
    public static void setUp() {
        String connectionString ="jdbc:postgresql://localhost:5432/mynews_test";
        Sql2o sql2o = new Sql2o(connectionString,"moringa","dammey5");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() {
        System.out.println("clearing databases");
        newsDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void add_addsNewsToDataBase() {
        News testNews = setUpNews();
        newsDao.add(testNews);
        assertEquals(1,newsDao.getAll().size());
    }

    @Test
    public void getAll_returnsAllNews() {
        News testNews = setUpNews();
        newsDao.add(testNews);
        News anotherNews = new News("Holiday","Holiday will start next week");
        newsDao.add(anotherNews);
        assertEquals(2,newsDao.getAll().size());
        assertEquals(testNews,newsDao.getAll().get(0));
    }

    @Test
    public void findById_findsNewsInTheDataBase() {
        News testNews = setUpNews();
        newsDao.add(testNews);
        News anotherNews = new News("Holiday","Holiday will start next week");
        newsDao.add(anotherNews);
        News foundNews = newsDao.findById(testNews.getId());
        assertEquals("Renaming",foundNews.getTitle());
    }

    @Test
    public void update_updatesNewsCorrectly() {
        String initialTitle = "Renaming";
        News testNews = setUpNews();
        newsDao.add(testNews);
        newsDao.update(testNews.getId(),"Holiday","Holiday will start next week");
        News updatedNews = newsDao.findById(testNews.getId());
        assertNotEquals(initialTitle,updatedNews.getTitle());


    }

    @Test
    public void deleteById_deletesNewsFromDatabase() {
        News testNews = setUpNews();
        newsDao.add(testNews);
        News anotherNews = new News("Holiday","Holiday will start next week");
        newsDao.add(anotherNews);
        newsDao.deleteById(testNews.getId());
        assertEquals(1,newsDao.getAll().size());
    }

    @Test
    public void clearAll_deletesAllNewsInDatabase() {
        News testNews = setUpNews();
        newsDao.add(testNews);
        News anotherNews = new News("Holiday","Holiday will start next week");
        newsDao.add(anotherNews);
        newsDao.clearAll();
        assertEquals(0,newsDao.getAll().size());
    }

    @Test
    public void addNewsToDepartment_AddsNewsCorrectly() {
        Department testDepartment = setUpDepartment();
        departmentDao.add(testDepartment);

        News testNews = setUpNews();
        newsDao.add(testNews);

        newsDao.addNewsToDepartment(testNews,testDepartment);
        assertEquals(1,newsDao.getAllDepartmentsForNews(testNews.getId()).size());
    }

    //helpers
    public News setUpNews() {
        return new News("Renaming","Our department will be renamed");
    }

    public Department setUpDepartment() {
        return new Department("Human Resource","caters for employees needs",15);
    }
}