package com.example.clinic;

public class WorkHours {
    private String date;
    private String startTime;
    private String endTime;
    private int slots;
    private int takenSlots;
    public WorkHours(){
        //Once again, snapshots form firebase need a no argument constructor
        //For some dumb reason idk anymore
    }
    public WorkHours(String date, String startTime, String endTime,int slots,int takenSlots){
        this.date=date;
        this.startTime=startTime;
        this.endTime=endTime;
        this.slots=slots;
        this.takenSlots=takenSlots;
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

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public int getTakenSlots() {
        return takenSlots;
    }

    public void setTakenSlots(int takenSlots) {
        this.takenSlots = takenSlots;
    }
}
