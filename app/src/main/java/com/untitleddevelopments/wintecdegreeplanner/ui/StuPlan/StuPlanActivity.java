package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        //Make all our references to our stu_plan.xml
        setContentView(R.layout.activity_stu_plan);
        SPTVStreamName = findViewById(R.id.SPTVStreamName);
        SPTVStuNameAndStuID = findViewById((R.id.SPTVStuNameAndStuID));
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
//        CustomViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        //viewPager.setPagingEnabled(false);          //GG ToDo talk to Navi
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        InsertStudent();                                //ToDo delete this once the rest of our team have set up students
    }
    @Override

    protected void onResume() {
        super.onResume();
        int student_ID = Globals.getStudent_ID();
        currentStudent = new Student(student_ID);
        currentStream = new Stream(currentStudent.getStream_ID());
        SPTVStuNameAndStuID.setText(currentStudent.getFullName()+ " " + currentStudent.getStudentID());
        SPTVStreamName.setText(currentStream.getName());
    }
    public void onClickEditStudent(View view){
        //ToDO Code this once Navi has done his stuff
        DisplayMessage("onClickEditStudent clicked ");
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo Navis edit Activity.class));

    }
    public void onClickDeleteStudent(View view){
        //ToDO Code this once Navi has done his stuff
        DisplayMessage("onClick Delete Student clicked ");
        Globals.setStudent(currentStudent);
        //startActivity(new Intent(this, ToDo Navis edit Activity.class));

    }

    private void DisplayMessage(String msg) {
        Log.i(TAG, msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


    private void InsertStudent() {      //delete this once team have set up students
        boolean inserted;
        ContentValues contentStudent = new ContentValues();
        contentStudent.put(DBHelper.STUDENT_FIRSTNAME, "Peter");
        contentStudent.put(DBHelper.STUDENT_SURNAME, "Jackson");
        contentStudent.put(DBHelper.STUDENT_STUDENTID, "7654123");
        contentStudent.put(DBHelper.STUDENT_PHOTOURI, "");
        contentStudent.put(DBHelper.STUDENT_STARTDATE, "12/06/2019");
        contentStudent.put(DBHelper.STUDENT_STATUS, 1);
        contentStudent.put(DBHelper.STUDENT_STREAM_ID, 2);
        DBManager.getInstance().openDatabase();
        inserted = DBManager.getInstance().insert(DBHelper.TBL_STUDENT, contentStudent);
        String myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
        Log.e(TAG, "Peter Jackson" + myMsg);
    }
}