package com.example.clinic;

public class Rate {
    private String clinicName;
    private String rating;
    private String comments;

    public Rate(){
    }

    public String getClinicName(){
        return clinicName;
    }

    public String getRating(){
        return rating;
    }

    public void setClinicName(String clinicName){
       this.clinicName = clinicName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
