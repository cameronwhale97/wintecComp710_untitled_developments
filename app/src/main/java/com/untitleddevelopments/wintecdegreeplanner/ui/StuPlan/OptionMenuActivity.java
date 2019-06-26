package com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.about_edit;
import com.untitleddevelopments.wintecdegreeplanner.about_screen;
import com.untitleddevelopments.wintecdegreeplanner.admin.AdminMainActivity;
import com.untitleddevelopments.wintecdegreeplanner.edit_student;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.modules.modules_main;

public class OptionMenuActivity extends AppCompatActivity {
    private static final String TAG = "OptionMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLogoInActionBar();
    }

    public void setLogoInActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.wintec_logo_logotipo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        if (PrefsManager.getUserType().equals("admin")) {
            Log.d(TAG, "onCreateOptionsMenu: admin ");
            menuInflater.inflate(R.menu.admin_top_menu, menu);
        } else {
            Log.d(TAG, "onCreateOptionsMenu: student ");
            menuInflater.inflate(R.menu.student_top_menu, menu);
        }
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent menuIntent;
        Log.d(TAG, "onOptionsItemSelected: [" + PrefsManager.getUserType() +"]");

        if (PrefsManager.getUserType().equals("admin")) {

            Log.d(TAG, "onOptionsItemSelected: admin switch "+item.getItemId());

            switch (item.getItemId()) {

                case R.id.miReturnToMain:
                    startActivity(new Intent(this, AdminMainActivity.class));
                    //                Toast.makeText(this, "Return To Main", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.miAbout:
                    startActivity(new Intent(this, about_screen.class));
                    return true;

                case R.id.miViewPathways:
                    Toast.makeText(this, "View Pathways", Toast.LENGTH_LONG).show();
                    return true;

                case R.id.miUpdateModules:
                    Log.d(TAG, "onOptionsItemSelected: update");
                    startActivity(new Intent(this, modules_main.class));
                    return true;

                case R.id.miUpdateApp:
                    startActivity(new Intent(this, about_edit.class));
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            } //switch
        } else {

            Log.d(TAG, "onOptionsItemSelected: stu switch "+item.getItemId());

            switch (item.getItemId()) {

                case R.id.miAbout:
                    startActivity(new Intent(this, about_screen.class));
                    return true;

//                case R.id.mStuEditMyDetails:
//                    startActivity(new Intent(this, about_screen.class));
//                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
}
