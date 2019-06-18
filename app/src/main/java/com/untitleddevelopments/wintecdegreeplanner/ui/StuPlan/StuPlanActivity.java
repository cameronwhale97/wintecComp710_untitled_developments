package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;

/**
 * This activity to control the student plan for both students and admin. It contains a tabbed activity
 * which is used for each year
 */
public class StuPlanActivity extends AppCompatActivity {
    private static final String TAG = "StuPlanActivity";
    //views and layouts...
    TextView SPTVStuNameAndStuID;
    TextView SPTVStreamName;
    CustomViewPager viewPager;
    LinearLayout SPstudentDetailsPanel;
    LinearLayout SPLLayDelStuPopup;
    //ViewPager viewPager;          //Now that wse are using customViewPager this is not needed
    private PageViewModel pageViewModel;
    Stream currentStream;                       // used to determine the student is still using the same stream
    int currentStudent_ID;                      //used to keep tabs on what student we are dealing with
    Student currentStudent;                     //throughout the entire activity
    String userType;                            //either "student" or "admin"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userType = PrefsManager.getUserType();
        Log.d(TAG, "onCreate: user type: " + userType);
        currentStudent_ID = -1;          //initialise to a wierd number
        //PageView model holds all references to our data...
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        //Make all our references to our xml...
        setContentView(R.layout.activity_stu_plan);
        SPstudentDetailsPanel = findViewById(R.id.SPstudentDetailsPanel);
        SPTVStreamName = findViewById(R.id.SPTVStreamName);
        SPTVStuNameAndStuID = findViewById((R.id.SPTVStuNameAndStuID));
        viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);

        if(userType == "student") {
            //We don't show student details to the student as the have no options to edit or delete themselves from here
            SPstudentDetailsPanel.setVisibility(View.GONE);
            Log.d(TAG, "onCreate: setting to invisible...");
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        viewPager = findViewById(R.id.view_pager);  This was the original code. GG setup CustomViewPager to stop
//        swiping in the. Tabbed activity - because want to use swiping to complete/uncomplete a module inside
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Everytime a tabs is touched the year is updated to keep a track of what tab the user is in
                //because tabbed activity keeps anticipating the next tab
                Globals.setYear(tab.getPosition());
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Globals.getStudent_ID() != currentStudent_ID){
            //We have a new student so initialise everything!
            currentStudent_ID = Globals.getStudent_ID();
            if(currentStudent_ID == -1) {
                Log.d(TAG, "onResume: MAJOR PROBLEM NO STUDENT ID IS ON GLOBALS*********************");
                finish();
            }
            //setup our Globals with new details...
            currentStudent = new Student(currentStudent_ID);
            currentStream = new Stream(currentStudent.getStream_ID());
            Globals.setStream(currentStream);

            pageViewModel.loadUpArrays();           //Freshly read from Database
        }
        SPTVStuNameAndStuID.setText(currentStudent.getFullName()+ ", " + currentStudent.getStudentID());
        SPTVStreamName.setText(currentStream.getName());
    }
    public void onClickEditStudent(View view){
        //ToDO Code this once Navi has done his stuff
        displayMessage("onClickEditStudent clicked "+ Integer.toString(currentStudent.getStudent_ID()));
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo Navs's edit Activity.class));

    }
    public void onClickDeleteStudent(View view){
        //ToDO Geoff
        displayMessage("onClick Delete Student clicked " + Integer.toString(currentStudent.getStudent_ID()));
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo
    }
    public void onbtnDelConfirmYes(){
        displayMessage("Yes Clicked");
    }
    public void onbtnDelConfirmNo(){
        displayMessage("No Clicked");
    }

    private void displayMessage(String msg) {
        Log.i(TAG, msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}