package com.example.clinic;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testfname(){
        User test  = new User("John", "Doe", "Employee", "JohnDoe");
        assertEquals("John", test.getName());
    }

    @Test
    public void testLname(){
        User test  = new User("John", "Doe", "Employee", "JohnDoe");
        assertEquals("Doe", test.getLastName());
    }

    @Test
    public void testRole(){
        User test  = new User("John", "Doe", "Employee", "JohnDoe");
        assertEquals("Employee", test.getRole());
    }

    @Test
    public void testUsername(){
        User test  = new User("John", "Doe", "Employee", "JohnDoe");
        assertEquals("JohnDoe", test.getUsername());
    }
}

