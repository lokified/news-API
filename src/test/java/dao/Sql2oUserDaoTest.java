package dao;

import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUserDaoTest {

    private static Connection conn;
    private static Sql2oUserDao userDao;

    @BeforeClass
    public static void setUp() {
        String connectionString ="jdbc:postgresql://localhost:5432/mynews_test";
        Sql2o sql2o = new Sql2o(connectionString,"moringa","dammey5");
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() {
        System.out.println("clearing databases");
        userDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingUsersSetsId() {
        User testUser = setUpUser();
        userDao.add(testUser);
        assertNotEquals(0,testUser.getId());
    }

    @Test
    public void addedUsersReturnedFormGetAll() {
        User testUser = setUpUser();
        userDao.add(testUser);
        assertEquals(1,userDao.getAll().size());
    }

    //helpers
    public User setUpUser(){
        return new User("loki","male","HR","Assign job",1);
    }
}