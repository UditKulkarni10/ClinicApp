package com.example.clinic;

public class WorkHours {
    private String date;
    private String startTime;
    private String endTime;
    public WorkHours(){
        //Once again, snapshots form firebase need a no argument constructor
        //For some dumb reason idk anymore
    }
    public WorkHours(String date, String startTime, String endTime){
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
