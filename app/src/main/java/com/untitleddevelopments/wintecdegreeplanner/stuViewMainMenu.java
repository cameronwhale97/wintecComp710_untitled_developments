package com.untitleddevelopments.wintecdegreeplanner;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.lang.reflect.Field;

public class stuViewMainMenu extends AppCompatActivity {

    ImageView iv;       //Reference image view at top of class

    //create this little sucker...
    public static int getResId(String resourceName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        } catch (Exception e) {
            throw new RuntimeException("No resource ID found for: "
                    + resourceName + " / " + c, e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuview_mainmenu);

        //pass in your string below... in this case I'm passing stream_db
        //I'm not sure if you need to have the ".png" at the end
        int resID = getResId("stream_db", R.drawable.class);
        //then you can do your find view by ID
        iv = findViewById(resID);



    }
}
