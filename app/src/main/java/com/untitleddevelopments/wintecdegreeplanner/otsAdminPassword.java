package com.untitleddevelopments.wintecdegreeplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class otsAdminPassword extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ots_admin_password);

        ImageView otsAdmin = findViewById(R.id.imageView6);







        otsAdmin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText admin_password = findViewById(R.id.editText2);
                String ad_password = admin_password.getText().toString();

                admin_password.getEditableText().toString();

                if (ad_password.equals("WinITDPM01")){
                    Intent adminlogindone = new Intent(otsAdminPassword.this, otsAdminDone.class);
                    startActivity(adminlogindone);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(otsAdminPassword.this);
                    builder.setTitle("Password is Incorrect");
                    builder.setMessage("Please check your password and try again");
                    builder.setPositiveButton("Okay", null);
                    AlertDialog dialog = builder.show();
                }


            }
        });
    }
}

