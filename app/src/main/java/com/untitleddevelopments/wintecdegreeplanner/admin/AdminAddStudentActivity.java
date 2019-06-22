package com.untitleddevelopments.wintecdegreeplanner.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.R;

import java.util.ArrayList;
import java.util.List;


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

    ArrayList<Stream> streams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_student);

        btnAdd = findViewById(R.id.btnAdd);
        btnMenu = findViewById(R.id.btnMenu);

        btnAdd.setOnClickListener(this);
        btnMenu.setOnClickListener(this);

        populateStreamsInDropdownList();
    }


    private void populateStreamsInDropdownList() {

        streams = Stream.getAllStreams();

        List<String> streamNames = getStreamNames();

        // Reference: stackoverflow.com
        // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spStreams);

        // Create an adapter to describe how the items are displayed,
        // adapters are used in several places in android.
        // There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, streamNames);

        // Set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    private List<String> getStreamNames() {
        List<String> streamNames = new ArrayList<String>();


        for(int i=0; i<streams.size(); i++) {
            streamNames.add(streams.get(i).getName());
        }

        return streamNames;
    }


    private int getStreamIdFromName(String streamName) {
       return 0;
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
