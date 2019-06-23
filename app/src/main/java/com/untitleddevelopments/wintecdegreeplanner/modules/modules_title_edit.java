package com.untitleddevelopments.wintecdegreeplanner.modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.DB.Module;

import com.untitleddevelopments.wintecdegreeplanner.R;

public class modules_title_edit extends AppCompatActivity {

    EditText codeText, titleText;
    int currentModuleId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_title_edit);

        currentModuleId = Globals.getModule_ID();
        Module currentModule = new Module(currentModuleId);

        codeText = (EditText)findViewById(R.id.module_edit_code);
        titleText = (EditText)findViewById(R.id.module_edit_title);

        codeText.setText(currentModule.getCode());
        titleText.setText(currentModule.getName());
    }
}
