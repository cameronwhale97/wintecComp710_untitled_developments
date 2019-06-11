package com.untitleddevelopments.wintecdegreeplanner.DB;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.Context;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;

public class DBManager {
    /************
     *
     * Created 28 May 2019, Geoff Genner
     * Edited 4 June, Bringing all of the database initial loading into this class
     *
     ***********/
    private static final String TAG = "DBManager";

    private static DBManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDB;

    public static synchronized  void initialiseInstance(SQLiteOpenHelper helper) {
        if(instance == null) {
            instance = new DBManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized  DBManager getInstance() {
        if(instance == null) {
            throw new IllegalStateException(
                    DBManager.class.getSimpleName() +
                            " is not initialised, call initializeInstance(..) method first."
            );
        }
        return instance;
    }

    public synchronized void openDatabase() {
        if(mOpenCounter.incrementAndGet() == 1){
            //Open new Database
            mDB = mDatabaseHelper.getWritableDatabase();
        }
    }//openDatabase

    public synchronized void closeDatabase() {
        if(mOpenCounter.decrementAndGet() == 0){
            //Close Database
            mDB.close();
        }
    } //closeDatabase

    public Cursor getDetails(String query){
        return mDB.rawQuery(query, null);
    } //getDetails

    public boolean insert(String tableName, ContentValues values) {
        long l = -1;
        try {
            Log.d(TAG, "Trying to insert: " + tableName + values);
            l = mDB.insert(tableName, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l != -1;
    } //insert

    public boolean update(String tableName, ContentValues values, String whereClause, String whereArgs[]) {
        try {
            mDB.update(tableName, values, whereClause, whereArgs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(String tableName, String delWhereClause, String[] delWhereArgs){
        try {
            mDB.delete(tableName, delWhereClause, delWhereArgs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }//delete

    private boolean databaseContainsData(){
        //Check to see if version data is in the version table if it is then return true
        openDatabase();
        String query = "SELECT * FROM " + DBHelper.TBL_APP_VERSION;
        Cursor cursor = DBManager.getInstance().getDetails(query);
        Log.e(TAG, "Get Database Query: " + query + "CursorCount: " + cursor.getCount());
        return (cursor != null && cursor.getCount() > 0);
    } //databaseContainsData

    public boolean ensureDatabaseExists(Context context) {
        boolean ret = true;
        if(!databaseContainsData()) ret = loadUpTheDB(context);
        return ret;
    }//ensureDatabaseExists


    private boolean loadUpTheDB(Context context){
        //the database is empty so load her up...
        String myMsg;           //used for log tag message
        boolean inserted;       //true when the SQL insert worked!

        Log.e(TAG, "Loading the database...");
        //******************************Easy one first load App Version.
        ContentValues contentAppVer = new ContentValues();
        contentAppVer.put(DBHelper.APP_VERSION_VERSION,"1.0");
        DBManager.getInstance().openDatabase();
        inserted = DBManager.getInstance().insert(DBHelper.TBL_APP_VERSION, contentAppVer);
        myMsg = inserted ? " Inserted Yay" : " Not inserted Bohoo";
        Log.e(TAG, "version 1.0 " + myMsg);

        //**************************** Load the module table
        try {
            InputStream is = context.getAssets().open("module.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentModule = new ContentValues();
                contentModule.put(DBHelper.MODULE_ID, Integer.parseInt(tokens[0]));
                contentModule.put(DBHelper.MODULE_CODE, tokens[1]);
                contentModule.put(DBHelper.MODULE_NAME, tokens[2]);
                contentModule.put(DBHelper.MODULE_DESCRIPTION, tokens[3]);
                contentModule.put(DBHelper.MODULE_NZQALEVEL, Integer.parseInt(tokens[4]));
                contentModule.put(DBHelper.MODULE_NZQACREDITS, Integer.parseInt(tokens[5]));
                if(tokens.length == 7) contentModule.put(DBHelper.MODULE_COREQ, tokens[6]);
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_MODULE, contentModule);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[0] + " " + tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
            return false;
        }
        //**************************** Load the stream table
        try {
            InputStream is = context.getAssets().open("stream.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentStream = new ContentValues();
                contentStream.put(DBHelper.STREAM_ID, Integer.parseInt(tokens[0]));
                contentStream.put(DBHelper.STREAM_NAME, tokens[1]);
                contentStream.put(DBHelper.STREAM_ICONURI, tokens[2]);
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_STREAM, contentStream);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
            return false;
        } //load stream
        //**************************** Load the module_stream table
        try {
            InputStream is = context.getAssets().open("module_stream.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentModStr = new ContentValues();
                contentModStr.put(DBHelper.MODSTR_MOD_ID, Integer.parseInt(tokens[0]));
                contentModStr.put(DBHelper.MODSTR_STR_ID, Integer.parseInt(tokens[1]));
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_MODSTR, contentModStr);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[0] + " " + tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
            return false;
        } //load module
        //**************************** Load the pre_req table
        try {
            InputStream is = context.getAssets().open("pre_req.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8"))
            );
            String line;
            reader.readLine();      //step over header in the CSV
            while( (line = reader.readLine()) != null) {
                String[] tokens = line.split("\t");
                ContentValues contentModStr = new ContentValues();
                contentModStr.put(DBHelper.PREREQ_MOD_ID, Integer.parseInt(tokens[0]));
                contentModStr.put(DBHelper.PREREQ_PREREQ_ID, Integer.parseInt(tokens[1]));
                DBManager.getInstance().openDatabase();
                inserted = DBManager.getInstance().insert(DBHelper.TBL_PREREQ, contentModStr);
                myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
                Log.e(TAG, tokens[0] + " " + tokens[1] + myMsg);
            }
        } catch (IOException ex){
            ex.printStackTrace();
            Log.e(TAG, "loadUpTheDB() got exception: " + ex);
            return false;
        } //load pre_req

        String msg = "Database created successfully";
        Log.i(TAG, msg);
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        return true;
    } //loadUpTheDB
} //DBManager
