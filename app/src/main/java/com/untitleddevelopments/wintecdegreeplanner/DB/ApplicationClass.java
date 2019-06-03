package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.app.Application;

import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initialiseInstance(
                new DBHelper(getApplicationContext())
        );


        // initializing the prefs manager
        PrefsManager.initPrefs(this);

    }

}//ApplicationClass
