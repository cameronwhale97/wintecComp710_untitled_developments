package com.untitleddevelopments.wintecdegreeplanner;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.global.Globals;
import com.untitleddevelopments.wintecdegreeplanner.global.PrefsManager;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.OptionMenuActivity;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.StuPlanActivity;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class stuViewMainMenu extends OptionMenuActivity {
    private static final String TAG = "MainActivity";
    Student student;
    Integer student_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuview_mainmenu);
        student_ID = Globals.getStudent_ID();
        student = new Student(student_ID);
     }

    public void onClickDBA(View view){
        writeToDB(4);
    }

    public void onClickMWD(View view){
        writeToDB(1);
    }
    public void onClickNE(View view){
        writeToDB(2);
    }
    public void onClickSE(View view){
        writeToDB(3);
    }

    private void writeToDB(int stream_ID){
        Log.d(TAG, "writeToDB: ");
        ContentValues contentStudent = new ContentValues();
        contentStudent.put(DBHelper.STUDENT_STREAM_ID, stream_ID);

        Boolean updatedOK = DBManager.getInstance().update(
                DBHelper.TBL_STUDENT,                          //pass in table name
                contentStudent,                                //pass in content values this can be one or many columns of a row.
                //in this case I am updating just code and name
                DBHelper.STUDENT_ID + "=?",        //pass in where clause - note the ?
                new String[] {Integer.toString(student_ID)});                         //pass in a String array - in this case my array is just 1 item
        String myMsg = updatedOK ? " Update Success!" : " Not Deleted - bugger";
        Log.d(TAG, "Update student with chosen moduel:.." + myMsg);
        startActivity(new Intent(this, StuPlanActivity.class));
    }
}
    /*
    Camerons old stuff

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




     */





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


