package com.untitleddevelopments.wintecdegreeplanner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.GeoffsSandpit.GeoffTest;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "DPMMessage";
    private String userType;        //from shared prefs - either "admin" or "student" "newApp"
    private String programmer;      //from shared prefs either cameron geoff maria jonah or navi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //Get the shared preferences...
        // ******** Programmers if you need to get your name into the shared preferences for testing
        //I can help you get them into your emulator - as it is a little tricky
        //
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userType = sharedPref.getString("userType", "newApp");
        programmer = sharedPref.getString("programmer", "noProg");
        switch (programmer){
            case "geoff":
                //do Geoffs stuff
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!databaseContainsData()) loadUpTheDB();
                Intent intent = new Intent(this, GeoffTest.class);
                startActivity(intent);
            case "cameron":
                //do Camerons stuff


            case "maria":
                //do Marias stuff


            case "navi":
                //do Navis stuff


            case "jonah":
                //do Jonahs stuff


            default:
                //run app in normal mode
        }

        switch (userType) {
            case "admin":
                //call the first admin screen
                displayToast("Calling admin screen");
            case "student":
                //call the first student screen
                displayToast("Calling student screen");
            default:
                //we must be in a first time setup situation
                //so load up the DB
                if(!databaseContainsData()) loadUpTheDB();
                displayToast("First time setup");
        }
        finish();   //we never go back to the splash so this removes this activity from memory.
    }
    private boolean databaseContainsData(){
        //Check to see if version data is in the version table if it is then return true
        DBManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + DBHelper.TBL_APP_VERSION;
        Log.e(TAG, "Get Database Query: " + query);
        Cursor cursor = DBManager.getInstance().getDetails(query);
        return (cursor != null && cursor.getCount() > 0);
    }

    private void loadUpTheDB(){
        //the database is empty so load her up...
        displayToast("loading database");
        //Easy one first load App Version.
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.APP_VERSION_VERSION,"1.0");
        DBManager.getInstance().openDatabase();
        boolean inserted = DBManager.getInstance().insert(DBHelper.TBL_APP_VERSION, contentValues);
        if (inserted) {
            Log.e(TAG, "Success inserted: " + "version 1.0");
        } else {
            Log.e(TAG, "Failed to insert: " + "version 1.0");
        }



    }
    private void displayToast(String message){
        //this is used for debugging
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
