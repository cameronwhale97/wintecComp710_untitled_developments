package com.untitleddevelopments.wintecdegreeplanner;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
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
public class manage_students extends AppCompatActivity
        implements View.OnClickListener,
        PopupMenu.OnMenuItemClickListener,
        AdapterView.OnItemClickListener {

    private final String TAG = "manage_students";

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

    /**
     * Button to show top menu
     */
    private ImageButton btnMenu;

    /**
     * list to store students fetched from DB
     */
    ArrayList<Student> students;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        lvStudents      = findViewById(R.id.ms_lvStudents);
        etSearchStudent = findViewById(R.id.ms_etSearchStudent);
        btnAddStudent   = findViewById(R.id.ms_btnAddStudent);
        btnMenu         = findViewById(R.id.ms_btnMenu);


        // setting event handlers for button to this class
        btnAddStudent.setOnClickListener(this);
        btnMenu.setOnClickListener(this);

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


        /**
         * preparing dummy data of student names
         */
        //FakeDB.populateStudentNames(dataStudents); // FIXME - use student names from DB



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
                manage_students.this,
                R.layout.student_cell,
                R.id.studentName, getStudentFullNames());

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
                manage_students.this.adapter.getFilter().filter(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * function creates the popup menu from xml and sets its event handler.
     * @param view    View/anchor on which the popup menu will be shown.
     */
    private void showTopMenu(View view) {

        Toast.makeText(this, "Top menu", Toast.LENGTH_LONG).show();

        // creating popup menu and setting up event handler
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);

        // loading menu from xml
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.admin_top_menu, popup.getMenu());
        popup.show();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            // show add student activity of admin
            case R.id.ms_btnAddStudent:
                startActivity(new Intent(this, activity_add_student.class));
                break;

            // show top menu
            case R.id.ms_btnMenu:
                //showTopMenu(view);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.miReturnToMain:
                Toast.makeText(this, "Return To Main", Toast.LENGTH_LONG).show();
                return true;

            case R.id.miAbout:
                startActivity(new Intent(this, about_screen.class));
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

        // getting student ID from pre-loaded student list from database
        int student_ID = students.get(position).getStudent_ID();

        //Toast.makeText(this, "Student ID:" + student_ID, Toast.LENGTH_LONG).show();

        // setting student ID in globals
        Globals.setStudent_ID(student_ID);

        // showing Student Plan Activity
        startActivity(new Intent(this, edit_student.class));

    }

}//AdminMainActivity
