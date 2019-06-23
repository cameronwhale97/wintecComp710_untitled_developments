package com.untitleddevelopments.wintecdegreeplanner.modules;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.R;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;

public class modules_main extends AppCompatActivity {

    private ListView lvModules;
    ArrayAdapter<String> adapter;
    EditText searchModule;

    ArrayList<String> dataModules = new ArrayList<String>();

    ArrayList<Module> modules;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_main);

        lvModules = findViewById(R.id.lvModules);
        searchModule = findViewById(R.id.searchModule);

        modules = Module.getAllModules();

        setupListAdapter();
    }


    private List<String> getModuleTitle() {
        List<String> moduleTitle = new ArrayList<String>();

        for(int i=0; i<modules.size(); i++) {
            moduleTitle.add(modules.get(i).getFullTitle());
        }

        return moduleTitle;

    }


    private void setupListAdapter(){

        // set data to Adapter
        adapter = new ArrayAdapter<String>(
                modules_main.this,
                R.layout.modules_cell,
                R.id.moduleTitle, getModuleTitle());

        lvModules.setAdapter(adapter);

//        lvModules.setOnItemClickListener(this);

        searchModule.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                modules_main.this.adapter.getFilter().filter(s);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}
