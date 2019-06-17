package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

public class StuPlanActivity extends AppCompatActivity {
    private static final String TAG = "StuPlanActivity";
    Cursor cursor;
    String query;
    Student currentStudent;
    Stream currentStream;
    TextView SPTVStuNameAndStuID;
    TextView SPTVStreamName;
    CustomViewPager viewPager;
    //ViewPager viewPager;
    private PageViewModel pageViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);  //***
        super.onCreate(savedInstanceState);
        //Make all our references to our stu_plan.xml
        setContentView(R.layout.activity_stu_plan);
        SPTVStreamName = findViewById(R.id.SPTVStreamName);
        SPTVStuNameAndStuID = findViewById((R.id.SPTVStuNameAndStuID));
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        viewPager = findViewById(R.id.view_pager);  This was the original code. GG setup CustomViewPager to stop
//        swiping in the. Tabbed activity - because want to use swiping to complete/uncomplete a module inside
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        //viewPager.setPagingEnabled(false);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Globals.setYear(tab.getPosition());
                //int gg = tab.getPosition();
                Log.d(TAG, "onTabSelected: " + Integer.toString(tab.getPosition()));
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
        //int vpno = viewPager.getCurrentItem();
        //Log.d(TAG, "*********************************onResume StuPlanActivity current no: "+ Integer.toString(vpno));
        int student_ID = Globals.getStudent_ID();
        currentStudent = new Student(student_ID);
        currentStream = new Stream(currentStudent.getStream_ID());
        Globals.setStream(currentStream);
        pageViewModel.loadUpArrays();       //***
        SPTVStuNameAndStuID.setText(currentStudent.getFullName()+ " " + currentStudent.getStudentID());
        SPTVStreamName.setText(currentStream.getName());
    }
    public void onClickEditStudent(View view){
        //ToDO Code this once Navi has done his stuff
        DisplayMessage("onClickEditStudent clicked "+ Integer.toString(currentStudent.getStudent_ID()));
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo Jonah's edit Activity.class));

    }
    public void onClickDeleteStudent(View view){
        //ToDO Geoff
        DisplayMessage("onClick Delete Student clicked " + Integer.toString(currentStudent.getStudent_ID()));
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo
    }

    private void DisplayMessage(String msg) {
        Log.i(TAG, msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}