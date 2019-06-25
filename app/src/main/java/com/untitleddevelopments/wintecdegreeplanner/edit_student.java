package com.untitleddevelopments.wintecdegreeplanner;

import android.app.VoiceInteractor;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.admin.AdminMainActivity;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.OptionMenuActivity;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


/**
 * This activity adds a new student in the database.
 */
public class edit_student extends OptionMenuActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    /**
     * Button to sve student in DB
     */
    private ImageButton btnAdd;

    /**
     * form fields
     */
    private EditText et_FName;
    private EditText et_LName;
    private EditText et_StudentID;
    private Spinner  spStreams;
    private EditText et_StartDate;
    Student s;

    ArrayList<Stream> streams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        btnAdd = findViewById(R.id.edit_btnAdd);

        // setting event handlers for buttons
        btnAdd.setOnClickListener(this);

        // setting up student form UI references from XML
        et_FName     = findViewById(R.id.edit_etFName);
        et_LName     = findViewById(R.id.edit_etLName);
        et_StudentID  = findViewById(R.id.edit_etStudentID);
        et_StartDate = findViewById(R.id.edit_etStartDate);
        spStreams   = findViewById(R.id.edit_etStreams);

        //Gets a student object
        s = new Student(Globals.getStudent_ID());

        //Auto populates the fields
        et_FName.setText(s.getFirstname());
        et_LName.setText(s.getSurname());
        et_StudentID.setText(s.getStudentID());
        et_StartDate.setText(s.getStartDate());

        //Populates the spinner
        populateStreamsInDropdownList();
    }

    //Deletes a student based on the Student_ID you pass in
    public static void deleteStudent(int sID) {
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
    }

    private void populateStreamsInDropdownList() {

        streams = Stream.getAllStreams();

        List<String> streamNames = getStreamNames();

        // Reference: stackoverflow.com
        // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list



        // Create an adapter to describe how the items are displayed,
        // adapters are used in several places in android.
        // There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, streamNames);

        // Set the spinners adapter to the previously created one.
        spStreams.setAdapter(adapter);
        spStreams.setSelection(s.getStream_ID() - 1);
    }

    private List<String> getStreamNames() {
        List<String> streamNames = new ArrayList<String>();


        for(int i=0; i<streams.size(); i++) {
            streamNames.add(streams.get(i).getName());
        }

        return streamNames;
    }

    private boolean isStudentFormComplete() {


        // ensure the first name is filled
        if (et_FName.getText().toString().length() == 0 ) {
            Toast.makeText(this, "Please fill the first name of student!", Toast.LENGTH_LONG).show();
            return false;
        }

        // ensure the last name is filled
        if (et_LName.getText().toString().length() == 0 ) {
            Toast.makeText(this, "Please fill the last name of student!", Toast.LENGTH_LONG).show();
            return false;
        }

        // ensure the student id is filled
        if (et_StudentID.getText().toString().length() == 0 ) {
            Toast.makeText(this, "Please fill the student ID!", Toast.LENGTH_LONG).show();
            return false;
        }

        // ensure that start date is filled
        if (et_StartDate.getText().toString().length() == 0 ) {
            Toast.makeText(this, "Please fill the start date!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    //Edits the student that is selected in Globals student id
    private boolean saveStudentInDatabase() {

        // before saving student, ensure that the form is filled completely.
        if(!isStudentFormComplete()) {
            return false;
        }

        String streamName = spStreams.getSelectedItem().toString();

        int streamID = getStreamIdFromName(streamName);

        Toast.makeText(this, "streamID: " + streamID, Toast.LENGTH_LONG).show();

        /**
         *
         * saving student in database
         *
         */
        ContentValues contentStudent = new ContentValues();
        contentStudent.put(DBHelper.STUDENT_FIRSTNAME, et_FName.getText().toString());
        contentStudent.put(DBHelper.STUDENT_SURNAME, et_LName.getText().toString() );
        contentStudent.put(DBHelper.STUDENT_STUDENTID, et_StudentID.getText().toString() );
        contentStudent.put(DBHelper.STUDENT_PHOTOURI, "");
        contentStudent.put(DBHelper.STUDENT_STARTDATE, et_StartDate.getText().toString() );
        contentStudent.put(DBHelper.STUDENT_STATUS, 1);
        contentStudent.put(DBHelper.STUDENT_STREAM_ID, streamID);

        boolean updatedOK = false;
        DBManager.getInstance().openDatabase();
        updatedOK = DBManager.getInstance().update(
                DBHelper.TBL_STUDENT,                          //pass in table name
                contentStudent,                                //pass in content values this can be one or many columns of a row.
                 DBHelper.STUDENT_ID + "=" + s.getStudent_ID(),       //pass in where
                null);                         //pass in a String array

        String myMsg = updatedOK ? " Update Success!" : " Not Deleted - bugger";
        Log.e(TAG,  myMsg);

        return true;
    }


    /**
     * function gets stream ID from database for given stream name
     */
    private int getStreamIdFromName(String streamName) {


        String query = "SELECT * FROM " + DBHelper.TBL_STREAM +
                " WHERE " + DBHelper.STREAM_NAME + " = '" + streamName + "'";

        Log.d(TAG, "getStreamIdFromName: " + query);

        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            // return stream id for given streamName
            return cursor.getInt(cursor.getColumnIndex(DBHelper.STREAM_ID));
        }


        // this should not happen!!!
        return 0;
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

            // save student in DB
            case R.id.edit_btnAdd:
                boolean status = saveStudentInDatabase();

                // checking the status of saving student in DB
                if(status) {
                    Toast.makeText(this, "Student Saved Successfully", Toast.LENGTH_LONG).show();
                }
                Intent i = new Intent(this, AdminMainActivity.class);
                startActivity(i);

                break;

            // show top menu
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


}//AdminAddStudentActivity
