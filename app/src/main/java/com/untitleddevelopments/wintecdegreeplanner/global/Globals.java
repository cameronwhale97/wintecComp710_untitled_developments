package com.untitleddevelopments.wintecdegreeplanner.global;

public class Globals {


    private static String userType;


    public static synchronized void setUserType(String userType) {
        Globals.userType = userType;
    }

    public static synchronized String getUserType() {
        return Globals.userType;
    }



}//Globals
