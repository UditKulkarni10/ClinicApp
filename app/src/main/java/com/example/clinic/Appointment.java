package com.example.clinic;

public class Appointment {

    private Clinic clinic;
    private Service service;
    private WorkHours workHour;
    private boolean overdue;

    public Appointment(){
        //something something firebase needs this
    }
    public Appointment(Clinic clinic, Service service, WorkHours workHour, boolean overdue){
        this.clinic=clinic;
        this.service=service;
        this.workHour=workHour;
        this.overdue=overdue;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
    public WorkHours getWorkHour(){
        return workHour;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
