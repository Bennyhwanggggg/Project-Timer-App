package benny.dev.tasktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] projection = {TasksContract.Columns._ID,
                                TasksContract.Columns.TASKS_NAME,
                                TasksContract.Columns.TASKS_DESCRIPTION,
                                TasksContract.Columns.TASKS_SORTORDER};
        ContentResolver contentResolver = getContentResolver();


        ContentValues values = new ContentValues();

        // to put new value to insert
//        values.put(TasksContract.Columns.TASKS_NAME, "New Task 1");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "Description 1");
//        values.put(TasksContract.Columns.TASKS_SORTORDER, 2);
//        Uri uri = contentResolver.insert(TasksContract.CONTENT_URI, values);

        // For update
//        values.put(TasksContract.Columns.TASKS_NAME, "Content Provider");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "new update");
//        int count = contentResolver.update(TasksContract.buildTaskUri(1), values, null, null); // put id into buildTaskUri

        // Select multiple for update
//        values.put(TasksContract.Columns.TASKS_SORTORDER, "99");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "Completed");
//        String selection = TasksContract.Columns.TASKS_SORTORDER + " = " + 2;
//        int count = contentResolver.update(TasksContract.CONTENT_URI, values, selection, null); // count is number of records updated

        // SQL Injection prevention
//        values.put(TasksContract.Columns.TASKS_SORTORDER, "99");
//        values.put(TasksContract.Columns.TASKS_DESCRIPTION, "SQL injection prevent");
//        String selection = TasksContract.Columns.TASKS_SORTORDER + " = ?";
//        String[] args = {"99"};
//        int count = contentResolver.update(TasksContract.CONTENT_URI, values, selection, args); // args have to be array; each values in args is replaced in the order they appear in the array
        
        // delete
//        int count = contentResolver.delete(TasksContract.buildTaskUri(3), null, null); // 3 is the id, can change to selection e.g
//        String selection = TasksContract.Columns.TASKS_DESCRIPTION + " = ?";
//        String[] args = {"For deletion"};
//        int count = contentResolver.delete(TasksContract.CONTENT_URI, selection, args); // look for tasks where description is "For deletion" to delete.

        Cursor cursor = contentResolver.query(TasksContract.CONTENT_URI, projection, null, null, TasksContract.Columns.TASKS_NAME);

//        Cursor cursor = contentResolver.query(TasksContract.buildTaskUri(3),
//                projection,
//                null,
//                null,
//                TasksContract.Columns.TASKS_SORTORDER);

        if(cursor != null){
            Log.d(TAG, "onCreate: number of rows = " + cursor.getCount());
            while (cursor.moveToNext()){
                for(int i=0; i<cursor.getColumnCount(); i++){
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: +++++++++++++++++++++++");
            }
            cursor.close();
        }
//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//
//        final SQLiteDatabase db = appDatabase.getReadableDatabase();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
