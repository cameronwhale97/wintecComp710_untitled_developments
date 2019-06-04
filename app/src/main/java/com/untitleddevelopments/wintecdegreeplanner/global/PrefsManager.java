package com.untitleddevelopments.wintecdegreeplanner.global;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PrefsManager {
    private static final String TAG = "DPMMessage";
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

    private final static String USER_TYPE = "USER_TYPE";
    private final static String PROGRAMMER = "PROGRAMMER";


    public static void initPrefs(Context context)
    {
        prefs = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public static void setUserType(String userType) {
        Log.d(TAG, "saving user type: " + userType);
        prefsEditor.putString(USER_TYPE, userType);
        prefsEditor.apply();
    }
    public static String getUserType(){
        return prefs.getString(USER_TYPE, "newApp" );
    }


    public static void setProgrammer(String programmer) {
        Log.d(TAG, "programmer: " + programmer);
        prefsEditor.putString(PROGRAMMER, programmer);
        prefsEditor.apply();
    }
    public static String getProgrammer(){
        return prefs.getString(PROGRAMMER, "noProg" );
    }
}//PrefsManager


