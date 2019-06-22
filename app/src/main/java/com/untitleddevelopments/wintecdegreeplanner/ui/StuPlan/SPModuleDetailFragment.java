package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.untitleddevelopments.wintecdegreeplanner.R;

public class SPModuleDetailFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.layout_sp_module_details, null);

        builder.setView(view)
                .setTitle("ModDetails");
        return builder.create();
    }
}
