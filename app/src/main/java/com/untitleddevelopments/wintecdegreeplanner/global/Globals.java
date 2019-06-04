package com.untitleddevelopments.wintecdegreeplanner.global;

public class Globals {

    private static String student_ID;
    private static String year;
    private static String stream;
    private static String module;

    public static synchronized void setStudent_ID(String student_ID) { Globals.student_ID = student_ID;    }
    public static synchronized String getStudent_ID() {
        return Globals.student_ID;
    }

    public static synchronized void setYear(String year) {
        Globals.year = year;
    }
    public static synchronized String getYear() {
        return Globals.year;
    }

    public static synchronized void setStream(String stream) {
        Globals.stream = stream;
    }
    public static synchronized String getStream() {
        return Globals.stream;
    }

    public static synchronized void setModule(String module) {
        Globals.module = module;
    }
    public static synchronized String getModule() {
        return Globals.module;
    }





}//Globals
