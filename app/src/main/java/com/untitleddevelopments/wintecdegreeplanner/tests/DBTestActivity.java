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
    /*
    *Created by Geoff 4/6/19
    * This gives examples of select, insert, delete
    * Updated 11/6/19 - fixed bug with delete and added and update test
     */
    private static final String TAG = "DBTestActivity";  //used for logCat

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);

        Log.d(TAG, "Got to DBTestActivity");
        testSelect();
        testInsert();
        testDelete();
        testUpdate();
        testReplace();
        finish();
    }
    private void testSelect() {
        //Try out a select with inner join...
        //Use Module class to neatly store the data in an arraylist
        //You DON'T have to do this but you will still have to use the cursor.get
        ArrayList<Module> modules = new ArrayList<Module>();
        String query = "SELECT * FROM " + DBHelper.TBL_MODULE +
                " INNER JOIN " + DBHelper.TBL_MODSTR +
                " ON " + DBHelper.TBL_MODULE + "." + DBHelper.MODULE_ID + " = " +
                DBHelper.TBL_MODSTR + "." + DBHelper.MODSTR_MOD_ID + " WHERE " +
                DBHelper.MODSTR_STR_ID + " = " + 1;
        Log.d(TAG, "Get Modules for stream 1: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
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
        //I know we have lots of modules in the DB .. so get the fifth one
        String geoff = modules.get(5).toString();
        Log.d(TAG, "module in arraylist is: " + geoff);

        //With this query I did not use an arraylist
        query = "SELECT * FROM " + DBHelper.TBL_APP_VERSION;
        cursor = DBManager.getInstance().getDetails(query);
        Log.d(TAG, "Get Database Query: " + query + "CursorCount: " + cursor.getCount());
        //return (cursor != null && cursor.getCount() > 0);
    }//testSelect

    private void testInsert() {
        boolean inserted;
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
        String myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
        Log.e(TAG, "Geoffs insane course" + myMsg);
    }//testInsert

    private void testDelete() {
        //I will get the max id then delete it...
        //Delete has different syntax to select and insert 3 paremeters are passed in
        boolean updatedOK = false;
        String mod_ID = "";
        String query = "SELECT MAX(" + DBHelper.MODULE_ID + ")" +
                " FROM " + DBHelper.TBL_MODULE;
        Log.d(TAG, "Get max module id " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        mod_ID = (cursor.moveToFirst() ? cursor.getString(0): "");      //This is the max id that we want to delete
        Log.e(TAG, "And the  module id to delete is:" + mod_ID);
        updatedOK = DBManager.getInstance().delete(
                DBHelper.TBL_MODULE,                            //pass in table name
                DBHelper.MODULE_ID + "=?",        //pass in where clause - note the ?
                new String[] {mod_ID});                         //pass in a String array - in this case my array is just 1 item
        String  myMsg = updatedOK ? " Deleted Success!" : " Not Deleted - bugger";
        Log.e(TAG,  myMsg);
    }//testDelete

    private void testUpdate() {
        //Update has different syntax again!
        // It needs 4 paremeters passed in see notes alongside each paremeter
        boolean updatedOK = false;
        String mod_ID = "";
        String query = "SELECT MIN(" + DBHelper.MODULE_ID + ")" +
                " FROM " + DBHelper.TBL_MODULE;
        Log.d(TAG, "Get min module id " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        mod_ID = (cursor.moveToFirst() ? cursor.getString(0): "");      //This is the max id that we want to delete
        Log.e(TAG, "And the  module id to update is:" + mod_ID);
        ContentValues contentModule = new ContentValues();
        contentModule.put(DBHelper.MODULE_CODE, "ABCD0007");
        contentModule.put(DBHelper.MODULE_NAME, "Geoffs Test Update to the module with the minimum id");
        updatedOK = DBManager.getInstance().update(
                DBHelper.TBL_MODULE,                          //pass in table name
                contentModule,                                //pass in content values this can be one or many columns of a row.
                                                              //in this case I am updating just code and name
                DBHelper.MODULE_ID + "=?",        //pass in where clause - note the ?
                new String[] {mod_ID});                         //pass in a String array - in this case my array is just 1 item
        String myMsg = updatedOK ? " Update Success!" : " Not Deleted - bugger";
        Log.e(TAG,  myMsg);
    }//testUpdate

    private void testReplace(){
//        This will insert if the PK is not there,
//        otherwise it replaces the row on the basis of the PK
        Log.d(TAG, "testReplace: ");
        ContentValues contentStuMod = new ContentValues();
        contentStuMod.put(DBHelper.STUMOD_STU_ID, 1);
        contentStuMod.put(DBHelper.STUMOD_MOD_ID, 1);
        contentStuMod.put(DBHelper.STUMOD_COMPLETED, 1);
        DBManager.getInstance().openDatabase();
        DBManager.getInstance().replace(DBHelper.TBL_STUMOD, contentStuMod);
    }

}//DBTestActivity
