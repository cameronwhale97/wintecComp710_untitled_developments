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

        final String TAG = "otsStudentPush";

        Log.e(TAG,"otsPush"+ots_stuFirstname);


        ImageView otsStudent = findViewById(R.id.imageView6);
        otsStudent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ots_stuFirstname = otsFirst.getText().toString();
                ots_stuLastname = otsLast.getText().toString();
                ots_stuID = otsID.getText().toString();

                otsStuPush();
                Intent stusignupDone = new Intent(otsStudentSignup.this, otsStudentDone.class);
                startActivity(stusignupDone);
               // finish();

            }
        });
    }

  private void otsStuPush() {
        boolean inserted;
        ContentValues contentModule = new ContentValues();
        contentModule.put(DBHelper.STUDENT_FIRSTNAME, ots_stuFirstname);
        contentModule.put(DBHelper.STUDENT_SURNAME, ots_stuLastname);
        contentModule.put(DBHelper.STUDENT_STUDENTID, ots_stuID);
        //contentModule.put(DBHelper.STUDENT_STREAM_ID, 0);
        contentModule.put(DBHelper.STUDENT_STARTDATE, "");
        contentModule.put(DBHelper.STUDENT_PHOTOURI, "");
        contentModule.put(DBHelper.STUDENT_STATUS, 1);

        DBManager.getInstance().openDatabase();
        inserted = DBManager.getInstance().insert(DBHelper.TBL_STUDENT, contentModule);
        String myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
        final String TAG = "otsStudentPush";
        Log.e(TAG, "Welcome!" + myMsg);
        Log.e(TAG,ots_stuFirstname);
    }
}

