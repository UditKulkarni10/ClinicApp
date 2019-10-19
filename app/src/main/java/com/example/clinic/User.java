package com.example.clinic;

public class User {
    private String name;
    private String role;
    private String username;
    private String lastName;
    public User(){
        //This is for grabbing info back from the database
        //Without this, it gives you some sort of error
        //"User is not a no argument constructor"
    }
    public User(String name,String lastName, String role, String username){
        this.name=name;
        this.username=username;
        this.lastName=lastName;
        this.role=role;
    }



    public String getName(){
        return name;
    }
    public String getLastName(){
        return lastName;
    }

    public String getRole(){
        return role;
    }
    public String getUsername(){
        return username;
    }



}
