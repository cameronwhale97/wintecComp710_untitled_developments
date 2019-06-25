package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.admin.AdminMainActivity;
import com.untitleddevelopments.wintecdegreeplanner.edit_student;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;

/**
 * This activity to control the student plan for both students and admin. It contains a tabbed activity
 * which is used for each year
 */
public class StuPlanActivity extends OptionMenuActivity {       //**TOOLBAR needs extends
    private static final String TAG = "StuPlanActivity";
    //views and layouts...
    TextView SPTVStuNameAndStuID;
    TextView SPTVStreamName;
    CustomViewPager viewPager;
    LinearLayout SPstudentDetailsPanel;
    LinearLayout SPLLayDelStuPopup;

        //ViewPager viewPager;          //Now that wse are using customViewPager this is not needed
    private PageViewModel pageViewModel;

    //Other objects and integers used throughout the Activity...
    int currentStudent_ID;                      //used to keep track on what student we are dealing with
    Student currentStudent;                     //throughout the entire activity
    int currentStream_ID;
    Stream currentStream;                       // used to determine the student is still using the same stream

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //**TOOLBAR

        Log.d(TAG, "onCreate: user type: " + PrefsManager.getUserType());
        //PageView model holds all references to our data...
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        //Make all our references to our xml...
        setContentView(R.layout.activity_stu_plan);
        SPstudentDetailsPanel = findViewById(R.id.SPstudentDetailsPanel);   //container for student details
        SPTVStreamName = findViewById(R.id.SPTVStreamName);
        SPTVStuNameAndStuID = findViewById((R.id.SPTVStuNameAndStuID));
        viewPager = findViewById(R.id.view_pager);                          //container for tabs
        TabLayout tabs = findViewById(R.id.tabs);

        if(PrefsManager.getUserType() == "student") {
            //We don't show student details to the student as the have no options to edit or delete themselves from here
            SPstudentDetailsPanel.setVisibility(View.GONE);
            Log.d(TAG, "onCreate: setting to invisible...");
        }
        currentStudent_ID = Globals.getStudent_ID();
        Log.d(TAG, "onCreate: stuID:"+ currentStudent_ID );
        currentStudent =  new Student(currentStudent_ID);
        currentStream_ID = currentStudent.getStream_ID();
        Globals.setStream_ID(currentStream_ID);
        currentStream = new Stream(currentStudent.getStream_ID());
        Log.d(TAG, "onCreate: stuID:"+ currentStudent_ID + " stream:"+currentStream_ID);
        pageViewModel.loadUpArrays();

        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        viewPager = findViewById(R.id.view_pager);  This was the original code. GG setup CustomViewPager to stop
//        swiping in the. Tabbed activity - because want to use swiping to complete/uncomplete a module inside
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Everytime a tabs is touched the year is updated to keep a track of what tab the user is in
                //because tabbed activity keeps anticipating the next tab
                int position = tab.getPosition();
                Globals.setYear(position);
                Log.d(TAG, "onTabSelected: " + position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }//onCreate

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Globals get student id: " + Globals.getStudent_ID());
        if((Globals.getStudent_ID() != currentStudent_ID) || Globals.getStream_ID() != currentStream_ID){
            //We have a new student or a new stream so initialise everything!
            currentStudent_ID = Globals.getStudent_ID();
            currentStudent = new Student(currentStudent_ID);
            currentStream_ID = currentStudent.getStream_ID();
            currentStream = new Stream(currentStream_ID);

            //setup our Globals with new details..
            Globals.setStream_ID(currentStream_ID);
            pageViewModel.loadUpArrays();           //Freshly read from Database as either stream or student has changed
        }
        //assign the name and stream ...
        SPTVStuNameAndStuID.setText(currentStudent.getFullName()+ ", " + currentStudent.getStudentID());
        SPTVStreamName.setText(currentStream.getName());
    }

    public void onClickEditStudent(View view){
        Globals.setStudent(currentStudent);
        startActivity(new Intent(this,  edit_student.class));
    } //onClickEditStudent

    public void onClickDeleteStudent(View view){

        new AlertDialog.Builder(this, R.style.AlertDialog)
                .setTitle("Delete Student")
                .setMessage("Do you really want to delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(StuPlanActivity.this, currentStudent.getFullName() +" has been Deleted", Toast.LENGTH_LONG).show();
                        deleteStudent(currentStudent_ID);
                        startActivity(new Intent(StuPlanActivity.this,  AdminMainActivity.class));
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    } //onClickDeleteStudent

    public static void deleteStudent(int sID) {
        //This method deletes a student from the DB - when given an ID
        // NOTE: Delete just changes the student status to the number zero.
        ContentValues contentStudent = new ContentValues();
        contentStudent.put(DBHelper.STUDENT_STATUS, 0);
        boolean updatedOK = false;
        DBManager.getInstance().openDatabase();
        updatedOK = DBManager.getInstance().update(
                DBHelper.TBL_STUDENT,                          //pass in table name
                contentStudent,                                //pass in content values this can be one or many columns of a row.
                DBHelper.STUDENT_ID + "=" + sID,       //pass in where
                null);                         //pass in a String array

        String myMsg = updatedOK ? " Update Success!" : " Not Deleted - bugger";

        Log.e(TAG,  myMsg);
    } //deleteStudent

//    private void displayMessage(String msg) {
//        Log.i(TAG, msg);
//        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
//    }
}