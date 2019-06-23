package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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

import java.lang.reflect.Field;

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
    Stream currentStream;                       // used to determine the student is still using the same stream
    int currentStudent_ID;                      //used to keep track on what student we are dealing with
    Student currentStudent;                     //throughout the entire activity
//    String userType;                            //either "student" or "admin"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);                 //**TOOLBAR

//        userType = PrefsManager.getUserType();
        Log.d(TAG, "onCreate: user type: " + PrefsManager.getUserType());
        currentStudent_ID = -1;          //initialise to a wierd number
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
        //ToDO Code this once Jonah has done his stuff
        displayMessage("onClickEditStudent clicked "+ Integer.toString(currentStudent.getStudent_ID()));
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo Navs's edit Activity.class));

    } //onClickEditStudent

    public void onClickDeleteStudent(View view){
        //ToDO Geoff
        displayMessage("onClick Delete Student clicked " + Integer.toString(currentStudent.getStudent_ID()));
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo
    } //onClickDeleteStudent

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