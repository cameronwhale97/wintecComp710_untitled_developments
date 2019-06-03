package com.untitleddevelopments.wintecdegreeplanner.global;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.R;


/**
 * global presistent data
 *
 */
public class PrefsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs_test);

        testPrefs();

    }


    private void testPrefs() {


        // NOTE - this is a one time job to initialise prefs manager
        // call it once in first activity

        String userType = "admin";

        // step-2: set a user type
        PrefsManager.setUserType(userType);

        // step-1: get user type and ensure that it is correct
        if( PrefsManager.getUserType() == userType) {
            Toast.makeText(this, "PrefsManager can set/get user type", Toast.LENGTH_LONG).show();
        }


    }

}//PrefsTestActivity
