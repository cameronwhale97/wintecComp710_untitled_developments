package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class otsStudentAdmin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_studentadmin);

        ImageView otsStudent = findViewById(R.id.otsStudent);
        otsStudent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent stusignup = new Intent(otsStudentAdmin.this, otsStudentSignup.class);
                startActivity(stusignup);
            }
        });

    }
}
