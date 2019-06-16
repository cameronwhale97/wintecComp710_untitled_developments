package com.untitleddevelopments.wintecdegreeplanner;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.FakeDB2;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.StuPlanActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private String userType;        //from prefsManager - either "admin" or "student" "newApp"
    private String programmer;      //from prefsManager either cameron geoff maria jonah or navi


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: --------------------  first line of code in main activity ----------------------------------------");
        //set up database if needed...
        if(!DBManager.getInstance().ensureDatabaseExists(this)) {
            Toast.makeText(this, "Warning: Database does not exist!!!", Toast.LENGTH_LONG).show();
        }

        //Get the shared preferences...
        // ******** Programmers if you need to get your name or userType into the shared preferences for testing
        //I can help you get them into your emulator - as it is a little tricky
        //
        PrefsManager.setProgrammer("geoff");
        PrefsManager.setUserType("admin");

       // PrefsManager.setProgrammer("cameron");
       // PrefsManager.setUserType("admin");

        userType = PrefsManager.getUserType();
        programmer = PrefsManager.getProgrammer();
        Log.d(TAG, "programmer is: " + programmer);
        Log.d(TAG, "user type is: " + userType);

        switch (programmer){
            case "geoff":
                //do Geoffs stuff
                Log.d(TAG," Got to geoff ");
                Globals.setStream_ID(1);
                Globals.setStudent_ID(1);
                Student testStu = new Student(1);
                //if student 1 does not exist - then student 0 is returned
                if(testStu.getStudent_ID() == 0 ) FakeDB2.InsertStudents();
                startActivity(new Intent(this, StuPlanActivity.class));
                return;
            case "cameron":
                //do Camerons stuff

                Log.d(TAG," Got to cameron ");

                Intent cw_intent = new Intent(this, otsStudentDone.class);
                startActivity(cw_intent);


            case "maria":
                //do Marias stuff


            case "navi":
                //do Navis stuff


            case "jonah":
                //do Jonahs stuff
                Intent jonahIntent = new Intent(this, about_screen.class);
                startActivity(jonahIntent);


            default:
                //run app in normal mode
        }

        switch (userType) {
            case "admin":
                //call the first admin screen
                displayToast("Calling admin screen");
            case "student":
                //call the first student screen
                displayToast("Calling student screen");
            default:
                //we must be in a first time setup situation
                //so load up the DB

                displayToast("First time setup");
        }
        //setContentView(R.layout.activity_main);  THIS IS NOT NEEDED
        finish();   //we never go back to the splash so this removes this activity from memory.
    }//onCreate


    private void displayToast(String message){
        //this is used for debugging
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}//MainActivity
