package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.app.Application;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initialiseInstance(
                new DBHelper(getApplicationContext())
        );
    }
}
