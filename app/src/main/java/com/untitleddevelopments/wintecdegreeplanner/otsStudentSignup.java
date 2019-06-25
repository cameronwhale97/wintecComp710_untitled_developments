package com.untitleddevelopments.wintecdegreeplanner;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;

import java.util.ArrayList;

public class otsStudentSignup extends AppCompatActivity {

    public static String ots_stuFirstname = "";
    public static String ots_stuLastname = "";
    public static String ots_stuID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_student_signup);

        final EditText otsFirst = (EditText)findViewById(R.id.editText2);
        final EditText otsLast = (EditText)findViewById(R.id.editText3);
        final EditText otsID = (EditText)findViewById(R.id.editText);

        final String TAG = "otsDone";
        Log.e(TAG,""+Globals.getStudent_ID());


        ImageView otsStudent = findViewById(R.id.imageView6);
        otsStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ots_stuFirstname = otsFirst.getText().toString();
                ots_stuLastname = otsLast.getText().toString();
                ots_stuID = otsID.getText().toString();

                Log.e(TAG,"otsOnClick "+ots_stuFirstname+" "+ots_stuLastname+" "+ots_stuID);
                otsStuPush();
                Intent stusignupDone = new Intent(otsStudentSignup.this, otsStudentDone.class);
                startActivity(stusignupDone);

                    final int x = 1;
                        Globals.setStudent_ID(x);
                        final String TAG = "otsDone";
                        Log.e(TAG,""+Globals.getStudent_ID());
               // finish();

            }
        });
    }

  private void otsStuPush() {
        boolean inserted;
        ContentValues contentModule = new ContentValues();
        contentModule.put(DBHelper.STUDENT_FIRSTNAME,ots_stuFirstname);
        contentModule.put(DBHelper.STUDENT_SURNAME, ots_stuLastname);
        contentModule.put(DBHelper.STUDENT_STUDENTID, ots_stuID);
        PrefsManager.setUserType("student");
        contentModule.put(DBHelper.STUDENT_STREAM_ID, 1);
        contentModule.put(DBHelper.STUDENT_STARTDATE, "");
        contentModule.put(DBHelper.STUDENT_PHOTOURI, "");
        contentModule.put(DBHelper.STUDENT_STATUS, 1);

        DBManager.getInstance().openDatabase();
        inserted = DBManager.getInstance().insert(DBHelper.TBL_STUDENT, contentModule);
        String myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
        final String TAG = "otsStudentPush";
        Log.e(TAG, "Welcome!" + myMsg);
    }
}

