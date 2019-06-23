package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

import org.w3c.dom.Text;

public class SPModuleDetailFragment extends Fragment {
    private TextView modCodeName;
    private TextView description;
    private TextView NZQALevel;
    private TextView preReqs;
    private TextView coReq;
    private TextView modType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {


        Log.d("SPModuleDetailFragment", "---- onCreateView ---");

        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.layout_sp_module_details, parent, false);
    }

    public Dialog onCreateDialog123(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.layout_sp_module_details, null);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setTitle("ModDetails");
        SPMod spMod = Globals.getSPMod();
        Module mod = new Module(spMod.getModule_ID());              //spmod does not contain some stuff so get the module

        modCodeName = view.findViewById(R.id.SPTVStuNameAndStuID);
        description = view.findViewById(R.id.SPTVModDetModDesc);
        NZQALevel = view.findViewById(R.id.SPTVModDetModNZQALevel);
        preReqs = view.findViewById(R.id.SPTVModDetModPreReqs);
        coReq = view.findViewById(R.id.SPTVModDetModCoReq);
        modType = view.findViewById(R.id.SPTVModDetModModType);

        modCodeName.setText(spMod.getCode() + " | " + spMod.getName());
        description.setText(mod.getDescription());
        NZQALevel.setText(mod.getNZQALevel());
        preReqs.setText(spMod.toStringPreReqs());
        coReq.setText(mod.getCoReq());
        modType.setText("TODO Geoff Mod type");

        return builder.create();
    }
}
