package collegeapps.myassignments;

/**
 * Created by dgreg_000 on 25/11/2015.
 *
 *  DBAdapter Activity | Handles all SQLite related tasks (DB Open/Close Connection, CRUD tasks
 *
 *          open() - Opens the DB for usage to a specific Activity
 *          view() - Opens the DB connection to view contents
 *          close() - Closes DB connection
 *          insertRow() - Handles SQLite insert data execution
 *          getAllRows() - Handles SQLite 'Select *' statements
 *          getARow() - Handles SQLite select statement for a specific row
 *          deleteRow() - Handles SQLite delete statement for a specific row
 *          updateRow() - Handles SQLite update statement for a specific row
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    //EditText field names:         '_id' for Cursors sake
    public static final String ID = "_id"; // Primary Key for each created assignment
    public static final String TITLE = "TITLE"; // Title of the Assignment
    public static final String SUB_NAME = "SUBJECT_NAME"; // Subject Name
    public static final String T_MARKS = "TOTAL_MARKS"; // Total Marks
    public static final String D_DUE = "DATE_D"; // Date Due

    // String Array to hold all EditText Field values
    public static final String[] ALL_EDITTEXTS = new String[] {ID, TITLE, SUB_NAME, T_MARKS, D_DUE};

    // DB info
    public static final String DATABASE_NAME = "assignments.db";
    public static final String DATABASE_TABLE = "assignments";
    public static final int DATABASE_VERSION = 2; // Version number must be incremented each time a new DB is made

    // SQL statement to Create
    public static final String CREATE_DB =
            "CREATE TABLE " + DATABASE_TABLE +
            " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT NOT NULL, "
            + SUB_NAME + " TEXT NOT NULL, "
            + T_MARKS + " INTEGER, "
            + D_DUE + " TEXT "
            + ");";


    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx){
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }// end DBAdapter Constructor

    // Open the DB connection
    public DBAdapter open() {
        Log.i("State", "CALLING open() (DBAdapter) FUNCTION");
        db = myDBHelper.getWritableDatabase();
        Log.i("State", "CALLED open() (DBAdapter) FUNCTION");
        return this;
    }// end open()

    // Open the DB connection to view contents
    public DBAdapter view(){
        Log.i("State", "CALLING view() (DBAdapter) FUNCTION");
        db = myDBHelper.getReadableDatabase();
        Log.i("State", "CALLED open() (DBAdapter) FUNCTION");
        return this;

    }// end view()

    // Close the DB connection
    public void close(){

        myDBHelper.close();
    }// end close()

    public long insertRow(String title, String subject, String mark, String DateDue) {
        Log.i("State", "CALLING insertRow(DBAdapter) FUNCTION");
        ContentValues insertValues = new ContentValues();

        // Putting data into the DB table based on Columns/Rows
        insertValues.put(TITLE, title); // Column, Row
        insertValues.put(SUB_NAME, subject);
        insertValues.put(T_MARKS, mark);
        insertValues.put(D_DUE, DateDue);

        // SQLite statement to insert data into the DB table
        Log.i("State", "CALLED insertRow(DBAdapter) FUNCTION");
        return db.insert(DATABASE_TABLE, null, insertValues);

    }// end insertRow


    // get all Rows
    public Cursor getAllRows() {
        Log.i("State", "CALLING getAllRows(DBAdapter) FUNCTION");
        String whereID = null; // doesn't look for specific row ID, always NULL
        // SQLite statement to get all rows in the DB
        Cursor c = db.query(true, DATABASE_TABLE, ALL_EDITTEXTS, whereID, null, null, null, null, null);
        // Move to first record if possible
        if (c != null) {
            c.moveToFirst();
        }// end if
        else {
            Log.e("ERROR", "CURSOR MOVE NOT POSSIBLE");
        }
        Log.i("State", "CALLED getAllRows(DBAdapter) FUNCTION");
        return c; // Contains the information of all rows

    }// end getAllRows

    // Gets a specific row in the Listview - necessary for updating / delete specific row
    public Cursor getARow(long rowId) {
        Log.i("State", "CALLING getARow(DBAdapter) FUNCTION");
        String whereID = ID + "=" + rowId; // looks for specific row ID
        // SQLite statement to get a row in the DB
        Cursor c = db.query(true, DATABASE_TABLE, ALL_EDITTEXTS, whereID, null, null, null, null, null);
        // Move to first record if possible
        if (c != null) {
            c.moveToFirst();
        }// end if
        else {
            Log.e("ERROR", "CURSOR MOVE NOT POSSIBLE");
        }
        Log.i("State", "CALLED getARow(DBAdapter) FUNCTION");
        return c; // Contains the information of a single row
    }// end getRow

    // Delete a specific row
    public boolean deleteRow(long rowId){
        Log.i("State", "CALLING deleteRow(DBAdapter) FUNCTION");
        String whereID = ID + "=" + rowId; // Used to look for a specific row ID
        Log.i("State", "CALLED deleteRow(DBAdapter) FUNCTION");
        // SQLite statement to delete a specific row
        return db.delete(DATABASE_TABLE, whereID, null) !=0;
    }// end deleteRow

    // Change an existing row
    public boolean updateRow(long rowId, String title, String subject, String mark, String DateDue){
        Log.i("State", "CALLING updateRow(DBAdapter) FUNCTION");
        String whereID = ID + "=" + rowId; // Used to look for a specific row ID
        ContentValues updateValues = new ContentValues();

        // Updating the data in columns/rows
        updateValues.put(TITLE, title);
        updateValues.put(SUB_NAME, subject);
        updateValues.put(T_MARKS, mark);
        updateValues.put(D_DUE, DateDue);

        Log.i("State", "CALLED updateRow(DBAdapter) FUNCTION");
        // SQLite statement to delete a specific row
        return db.update(DATABASE_TABLE, updateValues, whereID, null) !=0;
    }// end updateRow



    private static class DatabaseHelper extends SQLiteOpenHelper{
        // Constructor
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }// end DatabaseHelper

        @Override
        public void onCreate(SQLiteDatabase db){
            // Creating the DB
            db.execSQL(CREATE_DB);
            Log.i("State", "DB created");
        }// end onCreate

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // Destroy Old DB
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            Log.i("State", "Old DB destroyed");

            // Recreate DB
            onCreate(db);
            Log.i("State", "DB Created");
        }// end onUpgrade
    }// end DatabaseHelper

}// end DBAdapter
