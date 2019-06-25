package com.untitleddevelopments.wintecdegreeplanner.DB;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

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
    //constructor with _ID
    public Student(int student_ID, String firstname, String surname, String studentID, int stream_ID, String startDate, String photoURI, int status) {
        this.student_ID = student_ID;
        this.firstname = firstname;
        this.surname = surname;
        this.studentID = studentID;
        this.stream_ID = stream_ID;
        this.startDate = startDate;
        this.photoURI = photoURI;
        this.status = status;
    }
    //constructor passing in an ID (to get from DB

    public Student(int student_ID) {
        this.student_ID = student_ID;
        String query = "SELECT * FROM " + DBHelper.TBL_STUDENT +
                " WHERE " + DBHelper.STUDENT_ID + " = " + student_ID;
        Log.d(TAG, "getCurrentStudent: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            this.student_ID = student_ID;
            this.firstname = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_FIRSTNAME));
            this.surname = (cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_SURNAME)));
            this.studentID = (cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_STUDENTID)));
            this.stream_ID = (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_STREAM_ID))));
            this.startDate = (cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_STARTDATE)));
            this.photoURI = (cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_PHOTOURI)));
            this.status = (Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_STATUS))));
        } else {
            //ID not found in DB so return 0 - integers and blank strings
            this.student_ID = 0;
            this.firstname = "";
            this.surname = "";
            this.studentID = "";
            this.stream_ID = 0;
            this.startDate = "";
            this.photoURI = "";
            this.status = 0;
        }
        Log.d(TAG, "getCurrentStudent: ");

        this.firstname = firstname;
        this.surname = surname;
        this.studentID = studentID;
        this.stream_ID = stream_ID;
        this.startDate = startDate;
        this.photoURI = photoURI;
        this.status = status;
    }

    public static ArrayList<Student> getAllStudents() {

        ArrayList<Student> students = new ArrayList<Student>();

        String query = "SELECT * FROM " + DBHelper.TBL_STUDENT + " WHERE " + DBHelper.STUDENT_STATUS + "=1";

        Log.d(TAG, "-- getAllStudents: " + query);

        DBManager.getInstance().openDatabase();

        Cursor cursor = DBManager.getInstance().getDetails(query);


        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                int student_ID       = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_ID));

                String firstName       = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_FIRSTNAME));
                String lastName        = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_SURNAME));
                String studentID       = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_STUDENTID));
                int studentStreamID    = cursor.getInt(cursor.getColumnIndex(DBHelper.STUDENT_STREAM_ID));
                String startDate       = cursor.getString(cursor.getColumnIndex(DBHelper.STUDENT_STARTDATE));



                String msg = String.format("-- Student: firstName=%s", firstName);

                Log.d(TAG, msg);
                Student student = new Student(student_ID, firstName, lastName, studentID, studentStreamID, startDate,"", 1);
                students.add(student);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return students;

    }

    // here sid is the wintec id
    public static boolean doesStudentIdExists(String wintecID) {

        String query = "SELECT * FROM " + DBHelper.TBL_STUDENT +
                " WHERE " + DBHelper.STUDENT_STUDENTID + " = '" + wintecID + "'";
        Log.d(TAG, "doesStudentIdExists: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        return (cursor != null && cursor.getCount() > 0);
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

    public String getFullName() {return firstname + " " + surname;}

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

    // function checks if the given fullname matches
    // with the fullname of the student
    public boolean fullNameMatches(String fullname) {
        return fullname.equals(firstname + " " + surname);
    }


}//Student
