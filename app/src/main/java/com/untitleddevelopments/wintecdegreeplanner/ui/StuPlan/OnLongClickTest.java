package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.untitleddevelopments.wintecdegreeplanner.MainActivity;
import com.untitleddevelopments.wintecdegreeplanner.R;

public class OnLongClickTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_long_click_test);

        Button b = (Button) findViewById(R.id.popup_btn);

        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
              startActivity(new Intent(OnLongClickTest.this, ModulePopup.class));
              return false;
            }
        });
    }
}
