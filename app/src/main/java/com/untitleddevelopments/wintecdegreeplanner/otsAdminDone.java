package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.StuPlanActivity;

public class otsAdminDone extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_admin_done);

        ImageView otsDone = findViewById(R.id.imageView6);

        otsDone.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent StuPlanActivity = new Intent(otsAdminDone.this, StuPlanActivity.class);
                startActivity(StuPlanActivity);
            }
        });


    }
}
