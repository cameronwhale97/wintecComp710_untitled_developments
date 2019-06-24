package com.untitleddevelopments.wintecdegreeplanner;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.FakeDB2;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.admin.AdminAddStudentActivity;
import com.untitleddevelopments.wintecdegreeplanner.admin.AdminMainActivity;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.tests.DBTestActivity;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.OnLongClickTest;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.SPModuleDetail;
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
//        PrefsManager.setProgrammer("cameron");
//        PrefsManager.setUserType("admim");

        PrefsManager.setProgrammer("cameron");
        //PrefsManager.setUserType("admin");

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
                Student testStu = new Student(0);
                //if student 1 does not exist - then student 0 is returned
                if(testStu.getStudent_ID() == 0 ) FakeDB2.InsertStudents();
                startActivity(new Intent(this, StuPlanActivity.class));
//                startActivity(new Intent(this, SPModuleDetail.class));
//                startActivity(new Intent(this, DBTestActivity.class));
                break;

            case "cameron":
                //do Camerons stuff
                Log.d(TAG," Got to cameron ");
                Intent cw_intent = new Intent(this, otsDisclaimer.class);
                startActivity(cw_intent);
                break;

            case "maria":
                //do Marias stuff

                Intent mariaIntent = new Intent(this, OnLongClickTest.class);
                startActivity(mariaIntent);
                return;


            case "navi":
                //do Navis stuff


            case "jonah":
                //do Jonahs stuff
                Intent jonahIntent = new Intent(this, manage_students.class);
                startActivity(jonahIntent);


            default:
                //run app in normal mode
        }

        switch (userType) {
            case "admin":
                //startActivity(new Intent(this, AdminMainActivity.class));
                displayToast("Calling admin screen");

            case "student":
                //Cam strt his returning student view
                displayToast("Calling student screen");
            default:
                Intent cw_intent = new Intent(this, otsDisclaimer.class);
                startActivity(cw_intent);

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
