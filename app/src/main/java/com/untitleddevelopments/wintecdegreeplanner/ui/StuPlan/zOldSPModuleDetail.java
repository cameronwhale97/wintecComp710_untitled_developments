package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.os.Bundle;
import android.widget.TextView;
import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

public class zOldSPModuleDetail extends OptionMenuActivity {
    private static final String TAG = "zOldSPModuleDetail";

    private TextView modCodeName;
    private TextView description;
    private TextView NZQALevel;
    private TextView preReqs;
    private TextView coReq;
    private TextView modType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zold_activity_spmodule_detail);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SPMod spMod = Globals.getSPMod();
        Module mod = new Module(spMod.getModule_ID());              //spmod does not contain some stuff so get the module

        modCodeName = findViewById(R.id.SPTVStuNameAndStuID);
        description = findViewById(R.id.SPTVModDetModDesc);
        NZQALevel = findViewById(R.id.SPTVModDetModNZQALevel);
        preReqs = findViewById(R.id.SPTVModDetModPreReqs);
        coReq = findViewById(R.id.SPTVModDetModCoReq);
        modType = findViewById(R.id.SPTVModDetModModType);

        modCodeName.setText(spMod.getCode() + " | " + spMod.getName());
        description.setText(mod.getDescription());
        NZQALevel.setText(mod.getNZQALevel());
        preReqs.setText(spMod.toStringPreReqs());
        coReq.setText(mod.getCoReq());
        modType.setText("TODO Geoff Mod type");
    }
}
