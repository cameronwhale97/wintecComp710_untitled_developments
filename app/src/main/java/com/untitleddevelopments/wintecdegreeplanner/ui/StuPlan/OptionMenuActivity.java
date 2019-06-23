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
import com.untitleddevelopments.wintecdegreeplanner.about_screen;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;

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
        Log.d(TAG, "onCreateOptionsMenu: ");
        menuInflater.inflate(R.menu.admin_top_menu, menu);
/*
        if (PrefsManager.getUserType() == "admin") {
            menuInflater.inflate(R.menu.admin_top_menu, menu);
        } else {
            menuInflater.inflate(R.menu.admin_top_menu, menu);
        }
  */
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent menuIntent;
        switch (item.getItemId()) {

            case R.id.miReturnToMain:
                Toast.makeText(this, "Return To Main", Toast.LENGTH_LONG).show();
                return true;

            case R.id.miAbout:
                startActivity(new Intent(this, about_screen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
