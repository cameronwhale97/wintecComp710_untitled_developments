package com.untitleddevelopments.wintecdegreeplanner.DB;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBHelper extends SQLiteOpenHelper{
    /************
     *
     * Created 28 May 2019, Geoff Genner
     *
     ***********/
    private static final String TAG = "DPMMessage";

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DegPlan.sqlite";

    //*******************************  Module Table Constants **************************************
    public static final String TBL_MODULE = "module_table";
    public static final String MODULE_ID = "module_id";
    public static final String MODULE_CODE = "module_code";
    public static final String MODULE_NAME = "module_name";
    public static final String MODULE_DESCRIPTION = "module_description";
    public static final String MODULE_NZQALEVEL = "module_nzqalevel";
    public static final String MODULE_NZQACREDITS = "module_nzqacredits";
    public static final String MODULE_COREQ = "module_coreq";

    private String CREATE_TBL_MODULE = "CREATE TABLE " + TBL_MODULE + " (" +
            MODULE_ID + " INTEGER PRIMARY KEY," +
            MODULE_CODE + " TEXT NOT NULL UNIQUE, " +
            MODULE_NAME + " TEXT NOT NULL UNIQUE," +
            MODULE_DESCRIPTION + " TEXT NOT NULL, " +
            MODULE_NZQALEVEL + " INTEGER NOT NULL, " +
            MODULE_NZQACREDITS + " INTEGER NOT NULL," +
            MODULE_COREQ + " TEXT)";
    private final String DROP_TBL_MODULE = "DROP TABLE IF EXISTS " + TBL_MODULE;

    //*******************************  Stream Table Constants **************************************
    public static final String TBL_STREAM = "stream_table";
    public static final String STREAM_ID = "stream_id";
    public static final String STREAM_NAME = "stream_name";
    public static final String STREAM_ICONURI = "stream_icon_uri";


    private String CREATE_TBL_STREAM = "CREATE TABLE " + TBL_STREAM + " (" +
            STREAM_ID + " INTEGER PRIMARY KEY," +
            STREAM_NAME + " TEXT NOT NULL UNIQUE," +
            STREAM_ICONURI +" TEXT UNIQUE)";
    private final String DROP_TBL_STREAM = "DROP TABLE IF EXISTS " + TBL_STREAM;

    //*******************************  Student Table Constants **************************************
    public static final String TBL_STUDENT = "student_table";
    public static final String STUDENT_ID = "student_id";
    public static final String STUDENT_FIRSTNAME = "student_firstname";
    public static final String STUDENT_SURNAME = "student_surname";
    public static final String STUDENT_STUDENTID = "student_studentid";
    public static final String STUDENT_STREAM_ID = "student_stream_id";
    public static final String STUDENT_STARTDATE = "student_startdate";
    public static final String STUDENT_PHOTOURI = "student_photo_uri";
    public static final String STUDENT_STATUS = "student_status";       //1 = ACTIVE, 0 = DELETED


    private String CREATE_TBL_STUDENT = "CREATE TABLE " + TBL_STUDENT + " (" +
            STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            STUDENT_FIRSTNAME + " TEXT NOT NULL," +
            STUDENT_SURNAME + " TEXT NOT NULL," +
            STUDENT_STUDENTID + " TEXT, " +
            STUDENT_STREAM_ID + " INTEGER, " +
            STUDENT_STARTDATE + " TEXT, " +
            STUDENT_PHOTOURI + " TEXT, " +
            STUDENT_STATUS + " INT NOT NULL, " +
            "FOREIGN KEY(" + STUDENT_STREAM_ID + ") REFERENCES " + TBL_STREAM + "(" + STREAM_ID + ")" +
            ")";

    private final String DROP_TBL_STUDENT = "DROP TABLE IF EXISTS " + TBL_STUDENT;

    //*************************************** ModuleStream Table Constants **************************************
    public static final String TBL_MODSTR = "module_stream_table";
    public static final String MODSTR_MOD_ID = "module_stream_module_id";
    public static final String MODSTR_STR_ID = "module_stream_stream_id";


    private String CREATE_TBL_MODSTR = "CREATE TABLE " + TBL_MODSTR + " (" +
            MODSTR_MOD_ID + " INTEGER NOT NULL," +
            MODSTR_STR_ID + " INTEGER NOT NULL," +
            "PRIMARY KEY (" + MODSTR_MOD_ID + "," + MODSTR_STR_ID + ")," +
            "FOREIGN KEY(" + MODSTR_MOD_ID + ") REFERENCES " + TBL_MODULE + "(" + MODULE_ID + ")," +
            "FOREIGN KEY(" + MODSTR_STR_ID + ") REFERENCES " + TBL_STREAM + "(" + STREAM_ID + ")" +
            ")";

    private final String DROP_TBL_MODSTR = "DROP TABLE IF EXISTS " + TBL_MODSTR;

    //*************************************** Student Module Table Constants **************************************
    public static final String TBL_STUMOD = "stu_mod_table";
    public static final String STUMOD_STU_ID = "stu_mod_stu_id";
    public static final String STUMOD_MOD_ID = "stu_mod_mod_id";
    public static final String STUMOD_COMPLETED = "stu_mod_completed";

    private String CREATE_TBL_STUMOD = "CREATE TABLE " + TBL_STUMOD + " (" +
            STUMOD_STU_ID + " INTEGER NOT NULL," +
            STUMOD_MOD_ID + " INTEGER NOT NULL," +
            STUMOD_COMPLETED + " INTEGER NOT NULL," +
            "PRIMARY KEY (" + STUMOD_STU_ID  + "," + STUMOD_MOD_ID + ")," +
            "FOREIGN KEY(" + STUMOD_STU_ID + ") REFERENCES " + TBL_STREAM + "(" + STREAM_ID + ")," +
            "FOREIGN KEY(" + STUMOD_MOD_ID + ") REFERENCES " + TBL_MODULE + "(" + MODULE_ID + ")" +
            ")";

    private final String DROP_TBL_STUMOD = "DROP TABLE IF EXISTS " + TBL_STUMOD;



    //*************************************** PreReqisite Table Constants **************************************
    public static final String TBL_PREREQ = "prereq_table";
    public static final String PREREQ_MOD_ID = "prereq_module_id";
    public static final String PREREQ_PREREQ_ID = "prereq_prereq_id";


    private String CREATE_TBL_PREREQ = "CREATE TABLE " + TBL_PREREQ + " (" +
            PREREQ_MOD_ID + " INTEGER NOT NULL," +
            PREREQ_PREREQ_ID + " INTEGER NOT NULL," +
            "PRIMARY KEY (" + PREREQ_MOD_ID + "," + PREREQ_PREREQ_ID + ")," +
            "FOREIGN KEY(" + PREREQ_MOD_ID + ") REFERENCES " + TBL_MODULE + "(" + MODULE_ID + ")," +
            "FOREIGN KEY(" + PREREQ_PREREQ_ID + ") REFERENCES " + TBL_MODULE + "(" + MODULE_ID + ")" +
            ")";

    private final String DROP_TBL_PREREQ = "DROP TABLE IF EXISTS " + TBL_PREREQ;

    //*************************************** App Version Table Constants **************************************
    public static final String TBL_APP_VERSION = "app_version_table";
    public static final String APP_VERSION_ID = "app_version_id";
    public static final String APP_VERSION_VERSION = "app_version_module_id";


    private String CREATE_TBL_APP_VERSION = "CREATE TABLE " + TBL_APP_VERSION + " (" +
            APP_VERSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
            APP_VERSION_VERSION + " TEXT NOT NULL" +
            ")";

    private final String DROP_TBL_APP_VERSION = "DROP TABLE IF EXISTS " + TBL_APP_VERSION;

    // ******************************   Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "Creating Tables: ");
        db.execSQL(CREATE_TBL_MODULE);
        db.execSQL(CREATE_TBL_STREAM);
        db.execSQL(CREATE_TBL_STUDENT);
        db.execSQL(CREATE_TBL_MODSTR);
        db.execSQL(CREATE_TBL_STUMOD);
        db.execSQL(CREATE_TBL_PREREQ);
        db.execSQL(CREATE_TBL_APP_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        Log.e(TAG, "Deleting app version table: " + DROP_TBL_APP_VERSION);
        db.execSQL(DROP_TBL_APP_VERSION);
        Log.e(TAG, "Deleting pre-requsite table: " + DROP_TBL_PREREQ);
        db.execSQL(DROP_TBL_PREREQ);
        Log.e(TAG, "Deleting app version table: " + DROP_TBL_STUMOD);
        db.execSQL(DROP_TBL_STUMOD);
        Log.e(TAG, "Deleting module-stream table: " + DROP_TBL_MODSTR);
        db.execSQL(DROP_TBL_MODSTR);
        Log.e(TAG, "Deleting student table: " + DROP_TBL_STUDENT);
        db.execSQL(DROP_TBL_STUDENT);
        Log.e(TAG, "Deleting stream table: " + DROP_TBL_STREAM);
        db.execSQL(DROP_TBL_STREAM);
        Log.e(TAG, "Deleting module table: " + DROP_TBL_MODULE);
        db.execSQL(DROP_TBL_MODULE);
        onCreate(db);
    }
    public void dropDB(SQLiteDatabase db){
        db.execSQL(DROP_TBL_APP_VERSION);
        db.execSQL(DROP_TBL_PREREQ);
        db.execSQL(DROP_TBL_STUMOD);
        db.execSQL(DROP_TBL_MODSTR);
        db.execSQL(DROP_TBL_STUDENT);
        db.execSQL(DROP_TBL_STREAM);
        db.execSQL(DROP_TBL_MODULE);
        onCreate(db);
    }
}
