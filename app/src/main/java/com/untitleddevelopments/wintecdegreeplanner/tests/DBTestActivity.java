package com.untitleddevelopments.wintecdegreeplanner.tests;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBHelper;
import com.untitleddevelopments.wintecdegreeplanner.DB.DBManager;
import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.R;

import java.util.ArrayList;

public class DBTestActivity extends AppCompatActivity {
    private static final String TAG = "DPMMessage";  //used for logCat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        Log.d(TAG, "****************Got to DBTestActivity");
        testSelect();
        testInsert();
        testDelete();
        finish();
    }

    private void testSelect() {
        //Try out a select with inner join...
        //Use Module class to neatly store the data in an arraylist
        //You DON'T have to do this but you will still have to use the cursor.get
        String query;
        Cursor cursor;

        ArrayList<Module> modules = new ArrayList<Module>();
        query = "SELECT * FROM " + DBHelper.TBL_MODULE +
                " INNER JOIN " + DBHelper.TBL_MODSTR +
                " ON " + DBHelper.TBL_MODULE + "." + DBHelper.MODULE_ID + " = " +
                DBHelper.TBL_MODSTR + "." + DBHelper.MODSTR_MOD_ID + " WHERE " +
                DBHelper.MODSTR_STR_ID + " = " + 1;
        Log.e(TAG, "Get Modules for stream 1: " + query);
        DBManager.getInstance().openDatabase();
        cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Module module = new Module( //using the Module Class constructor
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_ID))),
                        cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_CODE)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_DESCRIPTION)),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NZQALEVEL))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_NZQACREDITS))),
                        cursor.getString(cursor.getColumnIndex(DBHelper.MODULE_COREQ))
                );
                modules.add(module);
                cursor.moveToNext();
            }
        }
        //Once in an arraylist we can play with it.
        //I know we have lots of modules in the DB .. so..
        String geoff = modules.get(5).toString();
        Log.d(TAG, "module in arraylist is: " + geoff);

        //With this query I did not use an arraylist
        query = "SELECT * FROM " + DBHelper.TBL_APP_VERSION;
        cursor = DBManager.getInstance().getDetails(query);
        Log.d(TAG, "Get Database Query: " + query + "CursorCount: " + cursor.getCount());
        //return (cursor != null && cursor.getCount() > 0);
    }

    private void testInsert() {
        boolean inserted;
        String myMsg;
        ContentValues contentModule = new ContentValues();
        contentModule.put(DBHelper.MODULE_ID, 100);
        contentModule.put(DBHelper.MODULE_CODE, "CompGeoff");
        contentModule.put(DBHelper.MODULE_NAME, "Geoffs Test Module");
        contentModule.put(DBHelper.MODULE_DESCRIPTION, "Geoffs test description");
        contentModule.put(DBHelper.MODULE_NZQALEVEL, 10);
        contentModule.put(DBHelper.MODULE_NZQACREDITS, 50);
        contentModule.put(DBHelper.MODULE_COREQ, "Coreq");
        DBManager.getInstance().openDatabase();
        inserted = DBManager.getInstance().insert(DBHelper.TBL_MODULE, contentModule);
        myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
        Log.e(TAG, "Geoffs insane course" + myMsg);
    }//testInsert

    private void testDelete() {
        //I will get the max id then delete it...
        String query;
        Cursor cursor;
        int mod_ID = 0;
        query = "SELECT MAX(" + DBHelper.MODULE_ID + ")" +
                " FROM " + DBHelper.TBL_MODULE;
        Log.e(TAG, "Get max module id " + query);
        DBManager.getInstance().openDatabase();
        cursor = DBManager.getInstance().getDetails(query);
        mod_ID = (cursor.moveToFirst() ? cursor.getInt(0): 0);
        Log.e(TAG, "And the  module id is:" + mod_ID);
    }//testDelete
}//DBTestActivity
