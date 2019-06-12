package com.untitleddevelopments.wintecdegreeplanner.global;

import com.untitleddevelopments.wintecdegreeplanner.DB.Student;

public class Globals {

    private static int student_ID;
    private static String year;
    private static int stream_ID;
    private static int module_ID;

    private static Student student;

    public static synchronized void setStudent_ID(int student_ID) { Globals.student_ID = student_ID;    }
    public static synchronized int getStudent_ID() {
        return Globals.student_ID;
    }

    public static synchronized void setYear(String year) {
        Globals.year = year;
    }
    public static synchronized String getYear() {
        return Globals.year;
    }

    public static synchronized void setStream_ID(int stream_ID) {
        Globals.stream_ID = stream_ID;
    }
    public static synchronized int getStream_ID() {
        return Globals.stream_ID;
    }

    public static synchronized void setModule_ID(int module_ID) {
        Globals.module_ID = module_ID;
    }
    public static synchronized int getModule_ID() {
        return Globals.module_ID;
    }

    public static synchronized void setStudent(Student student) {
        Globals.student = student;
    }
    public static synchronized Student getStudent() {
        return Globals.student;
    }





}//Globals
