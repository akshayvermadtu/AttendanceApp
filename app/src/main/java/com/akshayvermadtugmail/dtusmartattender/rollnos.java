package com.akshayvermadtugmail.dtusmartattender;

public class rollnos {

    private int roll_no_student;
    private String status;

    public rollnos(){}


    public rollnos(int roll_no_student) {
        this.roll_no_student = roll_no_student;
    }


    public rollnos(String status) {
        this.status = status;
    }


    public void setRoll_no_student(int roll_no_student) {
        this.roll_no_student = roll_no_student;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public int getRoll_no_student() {
        return roll_no_student;
    }


    public String getStatus() {
        return status;
    }

}
