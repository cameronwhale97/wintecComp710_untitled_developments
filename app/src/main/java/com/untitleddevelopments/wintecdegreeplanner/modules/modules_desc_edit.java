package com.untitleddevelopments.wintecdegreeplanner.modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.untitleddevelopments.wintecdegreeplanner.R;

public class modules_desc_edit extends AppCompatActivity {

    EditText descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_desc_edit);

        descText = (EditText) findViewById(R.id.module_edit_desc);
    }
}
