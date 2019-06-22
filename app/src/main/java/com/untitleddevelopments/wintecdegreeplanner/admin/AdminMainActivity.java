package com.untitleddevelopments.wintecdegreeplanner.admin;

import java.util.ArrayList;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.untitleddevelopments.wintecdegreeplanner.DB.FakeDB;
import com.untitleddevelopments.wintecdegreeplanner.R;




/**
 * Admin Main Activity
 * -----------------------------------------------------------
 *
 * This is admin's main screen which shows list of students
 * and provision to search for students.
 *
 * Author: Navjot Singh
 */
public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener{

    /**
     * list view for student names
     */
    private ListView lvStudents;

    /**
     * Adapter for student names list view
     */
    ArrayAdapter<String> adapter;

    /**
     * EditText for searching student
     */
    EditText etSearchStudent;

    /**
     * ArrayList to store student names
     */
    ArrayList<String> dataStudents = new ArrayList<String>();

    /**
     * Button to show add student/form screen/activity
     */
    FloatingActionButton btnAddStudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        lvStudents      = findViewById(R.id.lvStudents);
        etSearchStudent = findViewById(R.id.etSearchStudent);
        btnAddStudent   = findViewById(R.id.btnAddStudent);

        btnAddStudent.setOnClickListener(this);


        /**
         * preparing dummy data of student names
         */
        FakeDB.populateStudentNames(dataStudents); // FIXME - use student names from DB

        setupListAdapter();
    }


    private  void setupListAdapter() {
        // set data to Adapter
        adapter = new ArrayAdapter<String>(
                AdminMainActivity.this,
                R.layout.student_cell,
                R.id.studentName, dataStudents);

        lvStudents.setAdapter(adapter);

        /**
         * Note:
         * text watcher will update the list when text changes
         * search data when text changes in EditText
         */
        etSearchStudent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                AdminMainActivity.this.adapter.getFilter().filter(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnAddStudent:
                startActivity(new Intent(this, AdminAddStudentActivity.class));
                break;
        }
    }

}//AdminMainActivity
