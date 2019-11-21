package com.example.clinic;

public class Patient extends User {

    private static String role;

    public Patient(){
        //super(name,lastName,username);
        //This is for grabbing info back from the database
        //Without this, it gives you some sort of error
        //"User is not a no argument constructor"
    }

    public Patient(String name,String lastName, String username){
        super(name,lastName,username);
        role = "patient";

    }

    public String getRole(){
        return role;
    }
}
