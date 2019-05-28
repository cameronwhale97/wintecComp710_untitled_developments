package com.untitleddevelopments.wintecdegreeplanner.DB;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.concurrent.atomic.AtomicInteger;

public class DBManager {
    /************
     *
     * Created 28 May 2019, Geoff Genner
     *
     ***********/
    private static final String TAG = "degPlanMessage";

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
    }
    public synchronized void closeDatabase() {
        if(mOpenCounter.decrementAndGet() == 0){
            //Close Database
            mDB.close();
        }
    }
    public Cursor getDetails(String query){
        return mDB.rawQuery(query, null);
    }

    public boolean insert(String tableName, ContentValues values) {
        long l = -1;
        try {
            Log.e(TAG, "Trying to insert: " + tableName + values);
            l = mDB.insert(tableName, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return l != -1;
    }

    public boolean delete(String tableName){
        try {
            mDB.delete(tableName, null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
