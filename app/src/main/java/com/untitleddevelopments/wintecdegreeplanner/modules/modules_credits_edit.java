package com.untitleddevelopments.wintecdegreeplanner.modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.untitleddevelopments.wintecdegreeplanner.R;

public class modules_credits_edit extends AppCompatActivity {

    EditText nzqaCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_credits_edit);

        nzqaCredits = (EditText) findViewById(R.id.module_edit_credits);
    }
}
