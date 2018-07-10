package benny.dev.tasktimer;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// Only Content Provider can access this class so it is private.
// Basic Database class for the application.
class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = "AppDatabase";
    private static String DATABASE_NAME = "TaskTimer.db";
    public static final int DATABASE_VERSION = 1;

    // Implement app database as a singleton
    private static AppDatabase instance = null;

    private AppDatabase(Context context) { // making the constructor private is one of the step to make the class a singleton as we only one want instance of the database. We use a private static field to get this instance
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // factory is if you want to use your own cursor factory
        Log.d(TAG, "AppDatabase: constructor");
    }

    /**
     * Get an instance of the app's singleton database helper object.
     * <p>
     * Simple implementation which is not thread safe. Maybe use factory class?
     *
     * @param context the content provider context
     * @return a SQLite database helper object
     */
    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // build database table
        Log.d(TAG, "onCreate: starts");
        String sSQL; // use a string variable to facilitate logging
        sSQL = "CREATE TABLE " + TasksContract.TABLE_NAME + " (" + TasksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + TasksContract.Columns.TASKS_NAME + " TEXT NOT NULL, " + TasksContract.Columns.TASKS_DESCRIPTION + " TEXT, "
                + TasksContract.Columns.TASKS_SORTORDER + " INTEGER);";
        Log.d(TAG, sSQL);
        db.execSQL(sSQL);
        Log.d(TAG, "onCreate: ends");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion) {
            case 1:
                // upgrade logic from ver 1
                break;
            default:
                throw new IllegalStateException("Upgrade to unknown version: " + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }
}
