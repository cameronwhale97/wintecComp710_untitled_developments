package com.untitleddevelopments.wintecdegreeplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

public class DelStudentDialog extends AppCompatDialogFragment {
    private TextView name;
    private TextView id;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.layout_delete_student, null);
        builder.setView(view)
                .setTitle("Yes")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        name = view.findViewById(R.id.SPTVDelStuName);
        id = view.findViewById(R.id.SPTVDelStuId);
        Student student = Globals.getStudent();
        id.setText(student.getStudentID());
        id.setText(student.getFullName());
        return builder.create();
    }
}
