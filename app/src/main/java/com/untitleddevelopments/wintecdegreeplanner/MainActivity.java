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
import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.GeoffsSandpit.GeoffTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
        Cursor cursor = DBManager.getInstance().getDetails(query);
        Log.e(TAG, "Get Database Query: " + query + "CursorCount: " + cursor.getCount());
        return (cursor != null && cursor.getCount() > 0);
    }

    private void loadUpTheDB(){
        //the database is empty so load her up...
        String myMsg;           //used for log tag message
        boolean inserted;       //true when the SQL insert worked!

        Log.e(TAG, "Loading the database...");
        //******************************Easy one first load App Version.
        ContentValues contentAppVer = new ContentValues();
        contentAppVer.put(DBHelper.APP_VERSION_VERSION,"1.0");
        DBManager.getInstance().openDatabase();
        inserted = DBManager.getInstance().insert(DBHelper.TBL_APP_VERSION, contentAppVer);
        myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
        Log.e(TAG, "version 1.0 " + myMsg);

        //**************************** Load the module table
        try {
            InputStream is = getAssets().open("module.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentModule = new ContentValues();
                contentModule.put(DBHelper.MODULE_ID, Integer.parseInt(tokens[0]));
                contentModule.put(DBHelper.MODULE_CODE, tokens[1]);
                contentModule.put(DBHelper.MODULE_NAME, tokens[2]);
                contentModule.put(DBHelper.MODULE_DESCRIPTION, tokens[3]);
                contentModule.put(DBHelper.MODULE_NZQALEVEL, Integer.parseInt(tokens[4]));
                contentModule.put(DBHelper.MODULE_NZQACREDITS, Integer.parseInt(tokens[5]));
                if(tokens.length == 7) contentModule.put(DBHelper.MODULE_COREQ, tokens[6]);
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_MODULE, contentModule);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[0] + " " + tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        //**************************** Load the stream table
        try {
            InputStream is = getAssets().open("stream.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentStream = new ContentValues();
                contentStream.put(DBHelper.STREAM_ID, Integer.parseInt(tokens[0]));
                contentStream.put(DBHelper.STREAM_NAME, tokens[1]);
                contentStream.put(DBHelper.STREAM_ICONURI, tokens[2]);
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_STREAM, contentStream);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        //**************************** Load the module_stream table
        try {
            InputStream is = getAssets().open("module_stream.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentModStr = new ContentValues();
                contentModStr.put(DBHelper.MODSTR_MOD_ID, Integer.parseInt(tokens[0]));
                contentModStr.put(DBHelper.MODSTR_STR_ID, Integer.parseInt(tokens[1]));
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_MODSTR, contentModStr);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[0] + " " + tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        //**************************** Load the pre_req table
        try {
            InputStream is = getAssets().open("pre_req.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentModStr = new ContentValues();
                contentModStr.put(DBHelper.PREREQ_MOD_ID, Integer.parseInt(tokens[0]));
                contentModStr.put(DBHelper.PREREQ_PREREQ_ID, Integer.parseInt(tokens[1]));
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_PREREQ, contentModStr);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[0] + " " + tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    private void displayToast(String message){
        //this is used for debugging
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
