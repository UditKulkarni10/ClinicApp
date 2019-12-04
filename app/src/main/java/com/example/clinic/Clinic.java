package com.example.clinic;

import java.io.Serializable;

public class Clinic implements Serializable {
    private String clinicName;
    private String phoneNumber;
    private String address;
    private String Uid;

    public Clinic(){


    }

    public Clinic(String Uid,String clinicName,String phoneNumber,String address){
        this.clinicName=clinicName;
        this.phoneNumber=phoneNumber;
        this.address=address;
        this.Uid=Uid;
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

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
