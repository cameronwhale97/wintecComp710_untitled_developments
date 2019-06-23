package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.StuPlanActivity;

public class stuViewMainMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuview_mainmenu);

        ImageView streamDB = (ImageView) findViewById(R.id.stream_icon);
        //streamDB.setImageURI(Uri.parse());

    }
}
