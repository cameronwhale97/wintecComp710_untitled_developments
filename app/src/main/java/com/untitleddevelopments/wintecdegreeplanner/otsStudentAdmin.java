package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;

public class otsStudentAdmin extends AppCompatActivity {


    private static final String TAG = "OneTimeSetup" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_studentadmin);


        ImageView otsStudent = findViewById(R.id.otsStudent);
        ImageView otsAdmin = findViewById(R.id.ots_Admin);

        otsStudent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent stusignup = new Intent(otsStudentAdmin.this, otsStudentSignup.class);
                startActivity(stusignup);
            }
        });

        otsAdmin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent admin_signup = new Intent(otsStudentAdmin.this, otsAdminPassword.class);
                startActivity(admin_signup);
            }
        });


    }
}
