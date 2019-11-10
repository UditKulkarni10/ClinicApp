package com.example.clinic;

public class Service {

    private String name;
    private String role;

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
