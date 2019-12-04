package com.example.clinic;

import java.io.Serializable;

public class Service implements Serializable {

    private String name;
    private String role;
    public Service(){
        //Once again, snapshots form firebase need a no argument constructor
        //For some dumb reason idk anymore
    }
    public Service(String name, String role){
        this.name=name;
        this.role=role;
    }

    public String getName(){
        return name;
    }
    public String getRole(){
        return role;
    }
    public void setName(String newName){
        name=newName;
    }
    public void setRole(String newRole){
        role=newRole;
    }


}
