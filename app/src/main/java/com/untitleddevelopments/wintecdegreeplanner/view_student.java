package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;

public class view_student extends AppCompatActivity {


    TextView tvFname;
    TextView tvLname;
    TextView tvSID;
    TextView tvPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        //Get the student from the ID
        Student s = new Student(Globals.getStudent_ID());

        //Set up the views
        tvFname = findViewById(R.id.view_Fname);
        tvLname = findViewById(R.id.view_Lname);
        tvSID = findViewById(R.id.view_SID);
        tvPath = findViewById(R.id.view_Path);

        //Sets the views
        tvFname.setText(s.getFirstname());
        tvLname.setText(s.getSurname());
        tvSID.setText(s.getStudentID());
        tvPath.setText(String.valueOf(s.getStream_ID()));



    }

    //When edit is pressed
    public void onClickViewStudentsEdit(View v){
        Intent i = new Intent(this, edit_student.class);
        startActivity(i);
    }

    //When delete is pressed
    public void onClickViewStudentsDelete(View v){
        edit_student.deleteStudent(Globals.getStudent_ID());
        Toast.makeText(this, "Student Deleted Successfully", Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, manage_students.class);
        startActivity(i);

    }
}
