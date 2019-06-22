package com.untitleddevelopments.wintecdegreeplanner.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.R;


/**
 * This activity adds a new student in the database.
 */
public class AdminAddStudentActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener{

    /**
     * Button to sve student in DB
     */
    private ImageButton btnAdd;

    /**
     * Button to show top menu
     */
    private ImageButton btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_student);

        btnAdd = findViewById(R.id.btnAdd);
        btnMenu = findViewById(R.id.btnMenu);

        btnAdd.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    private void saveStudentInDB() {

        Toast.makeText(this, "Student Added Successfully", Toast.LENGTH_LONG).show();

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
            case R.id.btnAdd:
                saveStudentInDB();
                break;

            // show top menu
            case R.id.btnMenu:
                showTopMenu(view);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miReturnToMain:
                Toast.makeText(this, "Return To Main", Toast.LENGTH_LONG).show();

                return true;

            default:
                return false;
        }
    }

}//AdminAddStudentActivity
