package com.untitleddevelopments.wintecdegreeplanner.admin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.OptionMenuActivity;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.StuPlanActivity;


/**
 * Admin Main Activity
 * -----------------------------------------------------------
 *
 * This is admin's main screen which shows list of students
 * and provision to search for students.
 *
 * Author: Navjot Singh
 */
public class AdminMainActivity extends OptionMenuActivity
        implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private final String TAG = "AdminMainActivity";

    /**
     * List view for student names
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

    /**
     * List to store students fetched from DB
     */
    ArrayList<Student> students;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        lvStudents      = findViewById(R.id.lvStudents);
        etSearchStudent = findViewById(R.id.etSearchStudent);
        btnAddStudent   = findViewById(R.id.btnAddStudent);


        // setting event handlers for button to this class
        btnAddStudent.setOnClickListener(this);

        //
        // NOTE:-
        //--------------------------------------------------------------------------
        //
        // This may be redundant if Main Activity has already created the database.
        // Therefore, ensureDatabaseExists returns true and DB is not created again.
        // Set up database if needed...
        //--------------------------------------------------------------------------

        if(!DBManager.getInstance().ensureDatabaseExists(this)) {
            Toast.makeText(this, "Warning: Database does not exist!!!",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "-- DB exists -- ");
        }


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        /**
         * when the activity starts load all the student from the database
         * and fill the info in students array list
         */
        students = Student.getAllStudents();

        setupListAdapter();
    }


    private List<String> getStudentFullNames() {
        List<String> studentNames = new ArrayList<String>();


        for(int i=0; i<students.size(); i++) {
            studentNames.add(students.get(i).getFullName());
        }

        return studentNames;
    }

    private  void setupListAdapter() {
        // set data to Adapter
        adapter = new ArrayAdapter<String>(
                AdminMainActivity.this,
                R.layout.student_cell,
                R.id.studentName, getStudentFullNames());

        // sort the names in alphabetic order before showing in list
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });

        lvStudents.setAdapter(adapter);

        lvStudents.setOnItemClickListener(this);

        /**
         * Note:
         * text watcher will update the list when text changes
         * search data when text changes in EditText
         */
        etSearchStudent.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                /**
                 * NOTE:
                 * ------------------------------------------------------
                 *
                 * We should apply text filter on the list of students
                 * after the text has changed in the search box
                 */
                AdminMainActivity.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }




    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            // show add student activity of admin
            case R.id.btnAddStudent:
                startActivity(new Intent(this, AdminAddStudentActivity.class));
                break;


        }
    }

    /**
     * After sorting of students names, we need to manually iterate the student list
     * and compare fullnames to get the student id.
     */
    private int getStudentIdByFullName(String fullName) {
        for(int i=0; i<students.size(); i++) {
            Student student = students.get(i);

            if(fullName.equals(student.getFullName())) {
                return student.getStudent_ID();
            }
        }

        // this should never happen, if should never reach here!!!
        return 0;
    }


    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

        // getting student ID from pre-loaded student list from database
        Student student = students.get(position);
        student.showInfo();


        //int student_ID = student.getStudent_ID();
        int student_ID = getStudentIdByFullName(adapter.getItem(position));

        //Toast.makeText(this, "Student ID:" + student_ID, Toast.LENGTH_LONG).show();

        // setting student ID in globalsstu
        Globals.setStudent_ID(student_ID);

        // showing Student Plan Activity
        startActivity(new Intent(this, StuPlanActivity.class));

    }

}//AdminMainActivity
