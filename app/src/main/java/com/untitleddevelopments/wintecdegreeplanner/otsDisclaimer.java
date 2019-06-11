package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class otsDisclaimer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_disclaimer);

        ImageView otsDisclaimer_Okay_button = findViewById(R.id.ots_Admin);
        otsDisclaimer_Okay_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent stuadmin = new Intent(otsDisclaimer.this, otsStudentAdmin.class);
                startActivity(stuadmin);
            }
        });

    }


}
