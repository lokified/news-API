package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void User_instantiatesCorrectly() {
        User testUser = setUpUser();
        assertTrue(testUser instanceof User);
    }

    @Test
    public void User_instantiatesWithNameCorrectly() {
        User testUser = setUpUser();
        assertEquals("loki",testUser.getName());
    }

    @Test
    public void User_instantiatesWithGenderCorrectly() {
        User testUser = setUpUser();
        assertEquals("male",testUser.getGender());
    }

    @Test
    public void User_instantiatesWithPositionCorrectly() {
        User testUser = setUpUser();
        assertEquals("HR",testUser.getPositions());
    }

    @Test
    public void User_instantiatesWithRoleCorrectly() {
        User testUser = setUpUser();
        assertEquals("Assign job",testUser.getRole());
    }

    @Test
    public void User_instantiatesWithDepartmentIdCorrectly() {
        User testUser = setUpUser();
        assertEquals(1,testUser.getDepartmentId());
    }

    @Test
    public void setNameCorrectlySetsName() {
        User testUser = setUpUser();
        testUser.setName("cho");
        assertNotEquals("Loki",testUser.getName());
    }

    @Test
    public void setGenderCorrectlySetsGender() {
        User testUser = setUpUser();
        testUser.setGender("female");
        assertNotEquals("male",testUser.getGender());
    }

    @Test
    public void setPositionCorrectlySetsPosition() {
        User testUser = setUpUser();
        testUser.setPositions("finance");
        assertNotEquals("HR",testUser.getPositions());
    }

    @Test
    public void setRoleCorrectlySetsRoles() {
        User testUser = setUpUser();
        testUser.setRole("raise salary");
        assertNotEquals("Assign job",testUser.getRole());
    }

    @Test
    public void setDepartmentIdCorrectlySetsDepartmentId() {
        User testUser = setUpUser();
        testUser.setDepartmentId(2);
        assertNotEquals(1,testUser.getDepartmentId());
    }
    //helpers
    public  User setUpUser(){
        return new User("loki","male","HR","Assign job",1);
    }
}