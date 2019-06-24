package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.modules.modules_desc_edit;
import com.untitleddevelopments.wintecdegreeplanner.modules.modules_level_edit;
import com.untitleddevelopments.wintecdegreeplanner.modules.modules_title_edit;

import org.w3c.dom.Text;

public class ModulePopup extends AppCompatActivity implements OnClickListener {

    //CardView to point to editing components
    private CardView moduleTitle, modulePrescription, nzqaLevel, nzqaCredits, moduleType;

    //Bonus
    private CardView modulePreq, moduleCoreq;

    //TextView for content
    private TextView modulePrescriptionContent, moduleLevelContent, moduleCreditsContent, modulePrereqContent,
            moduleCoreqContent, moduleTypeContent, moduleFullTitle;

    int currentModuleId;
    int currentModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.7));

        currentModuleId = Globals.getModule_ID();
//        SPMod spMod = new SPMod();

        Module currentModule = new Module(currentModuleId);

        Toast.makeText(this, "Module ID:" + currentModuleId, Toast.LENGTH_LONG).show();

        //Ref to TextViews
        moduleFullTitle = findViewById(R.id.moduleTitle);
        modulePrescriptionContent = findViewById(R.id.modulePrescriptionContent);
        moduleLevelContent = findViewById(R.id.moduleLevelContent);
        moduleCreditsContent = findViewById(R.id.moduleCreditsContent);
        moduleCoreqContent = findViewById(R.id.moduleCoreqContent);

        //Not yet in the db
        moduleTypeContent = findViewById(R.id.moduleTypeContent);

        //Can't get to work using SPMod
        modulePrereqContent = findViewById(R.id.modulePrereqContent);

        //Set Text of Popup
        moduleFullTitle.setText(currentModule.getFullTitle());
        modulePrescriptionContent.setText(currentModule.getDescription());
        moduleLevelContent.setText("Level " + String.valueOf(currentModule.getNZQALevel()));
        moduleCreditsContent.setText(String.valueOf(currentModule.getNZQACredits()));
        moduleCoreqContent.setText(currentModule.getCoReq());

        //Temporary
        moduleTypeContent.setText("Core");

//        modulePrereqContent.setText(spMod.toStringPreReqs());

        //Not working
//        if((currentModule.getCoReq()).isEmpty()){
//            moduleCoreqContent.setText("None");
//        }else {
//            moduleCoreqContent.setText(currentModule.getCoReq());
//        }

        //View references for cardview
        moduleTitle = (CardView) findViewById(R.id.titleCard);
        modulePrescription = (CardView) findViewById(R.id.prescriptionCard);
        nzqaLevel = (CardView) findViewById(R.id.levelCard);
        nzqaCredits = (CardView) findViewById(R.id.creditsCard);
        moduleType = (CardView) findViewById(R.id.typeCard);


        //Cards onClickListeners
        moduleTitle.setOnClickListener(this);
        modulePrescription.setOnClickListener(this);
        nzqaLevel.setOnClickListener(this);
        nzqaCredits.setOnClickListener(this);

        //Confirm what code is vs module id
        moduleType.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()) {
            case R.id.titleCard : i = new Intent(this, modules_title_edit.class); startActivity(i); break;
            case R.id.prescriptionCard : i = new Intent(this, modules_desc_edit.class); startActivity(i); break;
            case R.id.levelCard : i = new Intent(this, modules_level_edit.class); startActivity(i); break;
            case R.id.creditsCard : i = new Intent(this, modules_level_edit.class); startActivity(i); break;
            default: break;
        }

    }
}
