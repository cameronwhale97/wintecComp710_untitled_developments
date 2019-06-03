package com.untitleddevelopments.wintecdegreeplanner.global;


import android.content.Context;
import android.content.SharedPreferences;

public class PrefsManager {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor prefsEditor;

    private final static String USER_TYPE = "USER_TYPE";

    public static void initPrefs(Context context)
    {
        prefs = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public static void setUserType(String userType) {
        prefsEditor.putString(USER_TYPE, userType);
        prefsEditor.commit();
    }

    public static String getUserType(){
        return prefs.getString(USER_TYPE, "newApp" );
    }


}//PrefsManager


