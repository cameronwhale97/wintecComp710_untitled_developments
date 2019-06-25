package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.OptionMenuActivity;

public class about_screen extends OptionMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_screen);

        //Get android version
        String release = Build.VERSION.RELEASE;
        //Sets the android version
        TextView androidVersion = (TextView) findViewById(R.id.TextView_About_AndroidVer);
        androidVersion.setText(release);

        //YOU SHALL NOT PASS (if you aren't an admin)
        ImageButton b = (ImageButton) findViewById(R.id.AboutScreenButton);
        if(PrefsManager.getUserType() != "admin"){
            b.setEnabled(false);
            b.setVisibility(View.GONE);
        }

        //Checks if we have a custom app version
        if(PrefsManager.getAppversion() != "noAppVer"){
            TextView appVer = (TextView) findViewById(R.id.TextView_About_AppVer);
            appVer.setText(PrefsManager.getAppversion());
        }


    }

    //Go to the edit screen
    public void startAboutEditActivity(View v){
        Intent i = new Intent(this, about_edit.class);
        startActivity(i);
    }
}
