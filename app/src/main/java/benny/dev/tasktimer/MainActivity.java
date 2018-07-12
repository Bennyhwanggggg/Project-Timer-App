package benny.dev.tasktimer;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapter.OnTaskClickListener{
    private static final String TAG = "MainActivity";

    // Whether or not the activity is in 2-pane modes, i.e landscape on tablet
    private boolean mTwoPane = false;

    private static final String ADD_EDIT_FRAGMENT = "AddEditFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(findViewById(R.id.task_details_container) != null){
            // the detail container will only be present in the large screen layouts (res/values-land and res/values-sw600dp
            // if this view is present, the activity should be in two-pane mode
            mTwoPane = true;
        }
//
//        String[] projection = {TasksContract.Columns._ID,
//                                TasksContract.Columns.TASKS_NAME,
//                                TasksContract.Columns.TASKS_DESCRIPTION,
//                                TasksContract.Columns.TASKS_SORTORDER};
//        ContentResolver contentResolver = getContentResolver();
//
//
//        ContentValues values = new ContentValues();

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

//        Cursor cursor = contentResolver.query(TasksContract.CONTENT_URI, projection, null, null, TasksContract.Columns.TASKS_NAME);

//        Cursor cursor = contentResolver.query(TasksContract.buildTaskUri(3),
//                projection,
//                null,
//                null,
//                TasksContract.Columns.TASKS_SORTORDER);
//
            // To move through query result
//        if(cursor != null){
//            Log.d(TAG, "onCreate: number of rows = " + cursor.getCount());
//            while (cursor.moveToNext()){
//                for(int i=0; i<cursor.getColumnCount(); i++){
//                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
//                }
//                Log.d(TAG, "onCreate: +++++++++++++++++++++++");
//            }
//            cursor.close();
//        }
//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//
//        final SQLiteDatabase db = appDatabase.getReadableDatabase();



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

        switch (id){
            case R.id.menumain_addTask:
                taskEditRequest(null);
                break;
            case R.id.menumain_showDurations:
                break;
            case R.id.menumain_settings:
                break;
            case R.id.menumain_showAbout:
                break;
            case R.id.menumain_generate:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEditClick(Task task) {
        taskEditRequest(task);
    }

    @Override
    public void onDeleteClick(Task task) {
        getContentResolver().delete(TasksContract.buildTaskUri(task.getId()), null, null);
    }

    private void taskEditRequest(Task task){
        Log.d(TAG, "taskEditRequest: starts");
        if(mTwoPane){
            Log.d(TAG, "taskEditRequest: Two pane mode");
            // get fragment manager to add fragment
            AddEditActivityFragment fragment = new AddEditActivityFragment();

            // for adding
            Bundle arguments = new Bundle();
            arguments.putSerializable(Task.class.getSimpleName(), task);
            fragment.setArguments(arguments);

            FragmentManager fragmentManager = getSupportFragmentManager();
            // FragmentTransaction queues up the changes and perform them
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.task_details_container, fragment); // replace instead of add. Replace will work even if nothing existed.
            fragmentTransaction.commit();

        } else {
            Log.d(TAG, "taskEditRequest: Single pane mode");
            // start the activity for the selected item id.
            Intent detailIntent = new Intent(this, AddEditActivity.class);
            if(task != null){ // editing a task
                detailIntent.putExtra(Task.class.getSimpleName(), task);
                startActivity(detailIntent);
            } else {
                // adding a new task
                startActivity(detailIntent);
            }
        }
    }
}
