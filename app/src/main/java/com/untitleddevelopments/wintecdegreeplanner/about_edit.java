package com.untitleddevelopments.wintecdegreeplanner;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.OptionMenuActivity;

public class about_edit extends OptionMenuActivity {

    EditText appVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_edit);

        //Get textview
        appVer = (EditText) findViewById(R.id.EditText_AboutEdit);
    }

    public void startSetAppVer(View v){
        //Updates the version if there is something entered
        if(appVer.getText().toString().matches("")){
            Toast.makeText(this, "No version entered.", Toast.LENGTH_SHORT).show();
        }else{
            PrefsManager.setAppVersion(appVer.getText().toString());
            Toast.makeText(this, "Version updated.", Toast.LENGTH_SHORT).show();

        }

        Intent i = new Intent(this, about_screen.class);
        startActivity(i);
        finish();

    }

}
