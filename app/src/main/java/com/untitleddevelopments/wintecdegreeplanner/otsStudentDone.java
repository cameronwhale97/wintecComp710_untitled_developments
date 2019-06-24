package com.untitleddevelopments.wintecdegreeplanner;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.StuPlanActivity;

public class otsStudentDone extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_student_done);

        ImageView otsDone = findViewById(R.id.imageView6);

        otsDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent stuViewMainMenu = new Intent(otsStudentDone.this, stuViewMainMenu.class);
                startActivity(stuViewMainMenu);
            }
        });


    }
}