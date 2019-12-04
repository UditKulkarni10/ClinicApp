package com.example.clinic;

import java.io.Serializable;
import java.util.ArrayList;

public class Employee extends User implements Serializable {

    private String role;
    private ArrayList<Service> serviceList;
    private String address;
    private String phoneNumber;
    private String clinicName;
    private ArrayList<String> acceptedPayments;
    private ArrayList<String> workHours;

    public Employee() {
        //super();
        //This is for grabbing info back from the database
        //Without this, it gives you some sort of error
        //"User is not a no argument constructor"
    }

    public Employee(String name, String lastName, String username) {
        super(name, lastName, username);
        role = "employee";
        serviceList = new ArrayList<>();
    }


    public String getRole() {
        return role;
    }

    public ArrayList<Service> getServiceList() {
        return serviceList;
    }

    public ArrayList<String> getAcceptedPayments() {
        return acceptedPayments;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getWorkHours() {
        return workHours;
    }
}

