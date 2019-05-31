package com.untitleddevelopments.wintecdegreeplanner.DB;

public class Student {
    private int student_ID;
    private String firstname;
    private String surname;
    private String studentID;
    private int stream_ID;
    private String startDate;
    private String photoURI;
    private int status;

    //constructor without _ID
    public Student(String firstname, String surname, String studentID, int stream_ID, String startDate, String photoURI, int status) {
        this.firstname = firstname;
        this.surname = surname;
        this.studentID = studentID;
        this.stream_ID = stream_ID;
        this.startDate = startDate;
        this.photoURI = photoURI;
        this.status = status;
    }
    //getter setter

    public int getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(int student_ID) {
        this.student_ID = student_ID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getStream_ID() {
        return stream_ID;
    }

    public void setStream_ID(int stream_ID) {
        this.stream_ID = stream_ID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPhotoURI() {
        return photoURI;
    }

    public void setPhotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
