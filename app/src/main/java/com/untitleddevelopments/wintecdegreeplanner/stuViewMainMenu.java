package com.untitleddevelopments.wintecdegreeplanner;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.OptionMenuActivity;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.StuPlanActivity;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class stuViewMainMenu extends OptionMenuActivity {


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


        final TextView tvdatabase = (TextView) findViewById(R.id.tvDBA);




        tvdatabase.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //Intent x = new Intent (stuViewMainMenu.this, StuPlanActivity.class);
              //startActivity(x);
          }
      }

        );

        ArrayList<Stream> streams = new ArrayList<Stream>();
        String query = "SELECT * FROM " + DBHelper.TBL_STREAM;
        //Log.d(TAG, "Get Modules for stream 1: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Stream stream = new Stream( //using the Module Class constructor

                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.STREAM_ID))),
                        cursor.getString(cursor.getColumnIndex(DBHelper.STREAM_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.STREAM_ICONURI))
                );
                streams.add(stream);

                cursor.moveToNext();
            }
        }

        final String TAG = "forLoop";
        for (int i = 0; i < streams.size(); i++) {
            String str = streams.get(i).getIconURI();
            int resID = getResId(str, R.drawable.class);



            Log.e(TAG,""+resID);
            Log.e(TAG,""+streams.get(i).getStream_ID()+" "+streams.get(i).getName()+" "+streams.get(i).getIconURI());


            ImageView ia = findViewById(R.id.imageView10);
            ImageView ib = findViewById(R.id.imageView11);
            ImageView ic = findViewById(R.id.imageView12);
            ImageView id = findViewById(R.id.imageView13);

            ia.setImageResource(resID);
            ib.setImageResource(resID +1);
            ic.setImageResource(resID +2);
            id.setImageResource(resID +3);
        }
    }



/*EXTREMELY WIP - TO CONSULT WITH GEOFF
    private void saveStudentInDatabase() {

        boolean inserted;
        final TextView tvdatabase = (TextView) findViewById(R.id.tvDBA);
        final String x = tvdatabase.getText().toString();

        int streamID = getStreamIdFromName(x);
        Log.d("TAG",""+streamID);

        ContentValues contentStudent = new ContentValues();
        contentStudent.put(DBHelper.STUDENT_STREAM_ID, 1);

        DBManager.getInstance().openDatabase();
        inserted = DBManager.getInstance().insert(DBHelper.TBL_STUDENT, contentStudent);
        String myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
        final String TAG = "otsStudentPush";
        Log.d(TAG,""+myMsg);
         }
*/

}
