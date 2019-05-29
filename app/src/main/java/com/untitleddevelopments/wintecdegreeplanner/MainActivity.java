package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.GeoffsSandpit.GeoffTest;

public class MainActivity extends AppCompatActivity {
    private String userType;        //from shared prefs - either "admin" or "student" "newApp"
    private String programmer;      //from shared prefs either cameron geoff maria jonah or navi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userType = sharedPref.getString("userType", "newApp");
        programmer = sharedPref.getString("programmer", "noProg");
        switch (programmer){
            case "geoff":
                //do Geoffs stuff
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
        //we get to here if no names are in shared preferences
        switch (userType) {
            case "admin":
                //call the first admin screen
                displayToast("Calling admin screen");
            case "student":
                //call the first student screen
                displayToast("Calling student screen");
            default:
                //we must be in a first time setup situation
                displayToast("First time setup");
        }
        Intent intent = new Intent(this, GeoffTest.class);
        startActivity(intent);
        finish();
    }
    private void displayToast(String message){
        //this is used for debugging
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
