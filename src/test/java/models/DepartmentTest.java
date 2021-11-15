package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {
    @Test
    public void Department_instantiatesCorrectly() {
        Department testDepartment = setUpDepartment();
        assertTrue(testDepartment instanceof Department);
    }

    @Test
    public void Department_instantiatesCorrectlyWithName() {
        Department testDepartment = setUpDepartment();
        assertEquals("Human Resource",testDepartment.getName());
    }

    @Test
    public void Department_instantiatesCorrectlyWithDescription() {
        Department testDepartment = setUpDepartment();
        assertEquals("caters for employees needs",testDepartment.getDescription());
    }

    @Test
    public void Department_instantiatesCorrectlyWithEmployees() {
        Department testDepartment = setUpDepartment();
        assertEquals(15,testDepartment.getEmployees());
    }

    @Test
    public void setNameCorrectlySetsName() {
        Department testDepartment = setUpDepartment();
        testDepartment.setName("Legal Affairs");
        assertNotEquals("Human Resource",testDepartment.getName());
    }

    @Test
    public void setDescriptionCorrectlySetsDescription() {
        Department testDepartment = setUpDepartment();
        testDepartment.setDescription("Deals with legal affairs");
        assertNotEquals("caters for employees needs",testDepartment.getDescription());
    }

    @Test
    public void setEmployeesCorrectlySetsEmployees() {
        Department testDepartment = setUpDepartment();
        testDepartment.setEmployees(20);
        assertNotEquals(15,testDepartment.getEmployees());
    }

    //helpers
    public Department setUpDepartment() {
        return new Department("Human Resource","caters for employees needs",15);
    }
}