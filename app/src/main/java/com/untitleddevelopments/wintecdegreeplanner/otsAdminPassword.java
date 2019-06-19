package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class otsAdminPassword extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_admin_password);

        ImageView otsStudent = findViewById(R.id.imageView6);
        otsStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent stusignupDone = new Intent(otsAdminPassword.this, otsStudentDone.class);
                startActivity(stusignupDone);
            }
        });
    }
}

