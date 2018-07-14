package benny.dev.tasktimer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import benny.dev.tasktimer.debug.TestData;

public class MainActivity extends AppCompatActivity implements CursorRecyclerViewAdapter.OnTaskClickListener,
        AddEditActivityFragment.OnSaveClicked,
        AppDialog.DialogEvents {
    public static final int DIALOG_ID_DELETE = 1;
    private static final String TAG = "MainActivity";
    private static final int DIALOG_ID_CANCEL_EDIT_UP = 3;

    // Whether or not the activity is in 2-pane modes, i.e landscape on tablet
    private boolean mTwoPane = false;

    public static final int DIALOG_ID_CANCEL_EDIT = 2;

    // show dialog box
    private AlertDialog mDialog = null; // module scope because we need to dismiss it in onStop e.g when orientation changes to avoid memory leak.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTwoPane = (getResources().getConfiguration().orientation) == Configuration.ORIENTATION_LANDSCAPE;
        Log.d(TAG, "onCreate: two pane is " + mTwoPane);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // if AddEditActivity Fragment exist, we're editing
        boolean editing = fragmentManager.findFragmentById(R.id.task_details_container) != null;
        Log.d(TAG, "onCreate: is editing is: " + editing);

        // We need reference to the container, so we can show or hide appropriately.
        // No need to cast them as we are calling methods available for all views.
        View addEditLayout = findViewById(R.id.task_details_container);
        View mainFragment = findViewById(R.id.fragment);

        if (mTwoPane) {
            Log.d(TAG, "onCreate: in landscape mode.");
            mainFragment.setVisibility(View.VISIBLE);
            addEditLayout.setVisibility(View.VISIBLE);
        } else if (editing) {
            Log.d(TAG, "onCreate: on create single pane editing");
            // otherwise we  are editing in single pane mode where we need to only show addeditfragment
            mainFragment.setVisibility(View.GONE);
        } else {
            // not editing in single pane so just show main
            Log.d(TAG, "onCreate: not editing");
            mainFragment.setVisibility(View.VISIBLE);
            // Hide the editing frame
            addEditLayout.setVisibility(View.GONE); // GONE allow the space to become available while invisible still occupies the space.
        }


        // Only used when we had another layout for landscape
//        if (findViewById(R.id.task_details_container) != null) {
//            // the detail container will only be present in the large screen layouts (res/values-land and res/values-sw600dp
//            // if this view is present, the activity should be in two-pane mode
//            mTwoPane = true;
//        }
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
    public void onSaveClick() {
        Log.d(TAG, "onSaveClick: starts");
        // remove fragment after click.
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.task_details_container);
        if (fragment != null) {
            // can simplify to one line.
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }

        View addEditLayout = findViewById(R.id.task_details_container);
        View mainFragment = findViewById(R.id.fragment);

        if (!mTwoPane) {
            // we have just removed the editing fragment after save so we need to remove it
            addEditLayout.setVisibility(View.GONE);
            // make sure main activity fragment is visible
            mainFragment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(BuildConfig.DEBUG) {
            MenuItem generate = menu.findItem(R.id.menumain_generate);
            generate.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menumain_addTask:
                taskEditRequest(null);
                break;
            case R.id.menumain_showDurations:
                startActivity(new Intent(this, DurationsReport.class));
                break;
            case R.id.menumain_settings:
                break;
            case R.id.menumain_showAbout:
                showAboutDialog();
                break;
            case R.id.menumain_generate:
                TestData.generateTestData(getContentResolver());
                break;
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: Home button pressed");
                AddEditActivityFragment fragment = (AddEditActivityFragment) getSupportFragmentManager().findFragmentById(R.id.task_details_container);
                if(fragment.canClose()){
                    return super.onOptionsItemSelected(item); // we don't want to show the dialog so let android handle this
                } else {
                    showConfirmationDialog(DIALOG_ID_CANCEL_EDIT_UP);
                    return true; // indicate we are handling this.
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    public void showAboutDialog() {
        @SuppressLint("InflateParams") View messageView = getLayoutInflater().inflate(R.layout.about, null, false); // no root view we can sensibly use
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setView(messageView);
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: messageView on click");
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });

        mDialog = builder.create();
        mDialog.setCanceledOnTouchOutside(true);

        TextView tv = messageView.findViewById(R.id.about_version);
        tv.setText("v" + BuildConfig.VERSION_NAME);

        mDialog.show();
    }

    // Old ver
//@SuppressLint("SetTextI18n")
//    public void showAboutDialog(){
//        @SuppressLint("InflateParams") View messageView = getLayoutInflater().inflate(R.layout.about, null, false); // no root view we can sensibly use
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(R.string.app_name);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setView(messageView);
//
//        mDialog = builder.create();
//        mDialog.setCanceledOnTouchOutside(true);
//
//        messageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: messageView on click");
//                if(mDialog!= null && mDialog.isShowing()){
//                    mDialog.dismiss();
//                }
//            }
//        });
//
//        TextView tv = (TextView) messageView.findViewById(R.id.about_version);
//        tv.setText("v"+BuildConfig.VERSION_NAME);
//
//        mDialog.show();
//    }

    @Override
    public void onEditClick(@NonNull Task task) {
        taskEditRequest(task);
    }

    @Override
    public void onDeleteClick(@NonNull Task task) {
        // call dialog to confirm delete before actual delete
        Log.d(TAG, "onDeleteClick: starts");
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_DELETE); // put DIALOG_ID_DELETE into AppDialog.DIALOG_ID
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.deldiag_message));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.deldiag_positive_caption);

        // put args in bundle to be passed into dialog
        args.putLong("TaskId", task.getId());

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), null);

        // if not commented, when dialog popup delete occurs anyway
//        getContentResolver().delete(TasksContract.buildTaskUri(task.getId()), null, null); // this uses contentresolvers method to delete

    }

    private void taskEditRequest(Task task) {
        Log.d(TAG, "taskEditRequest: starts");
//        if (mTwoPane) { // when we had different layout for landscape and potrait

        Log.d(TAG, "taskEditRequest: Two pane mode");
        // get fragment manager to add fragment
        AddEditActivityFragment fragment = new AddEditActivityFragment();

        // for adding
        Bundle arguments = new Bundle();
        arguments.putSerializable(Task.class.getSimpleName(), task);
        fragment.setArguments(arguments);

        Log.d(TAG, "taskEditRequest: two pane mode");
        FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentTransaction queues up the changes and perform them
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.task_details_container, fragment); // replace instead of add. Replace will work even if nothing existed.
        fragmentTransaction.commit();

//        } else {
//            Log.d(TAG, "taskEditRequest: Single pane mode");
//        // start the activity for the selected item id.
//        Intent detailIntent = new Intent(this, AddEditActivity.class);
//        if (task != null) { // editing a task
//            detailIntent.putExtra(Task.class.getSimpleName(), task);
//            startActivity(detailIntent);
//        } else {
//            // adding a new task
//            startActivity(detailIntent);
//        }

        // Only one layout
        if (!mTwoPane) {
            Log.d(TAG, "taskEditRequest: single pane mode");
            // Hide the left hand fragment and show the right frame
            View mainFragment = findViewById(R.id.fragment);
            View addEditLayout = findViewById(R.id.task_details_container);
            mainFragment.setVisibility(View.GONE);
            addEditLayout.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "taskEditRequest: ends");

    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: starts");
        // use dialogId to decide which instance is calling it and correspond accordingly.
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                // delete after user confirms
                Long taskId = args.getLong("TaskId"); // "TaskId" matches what we did above in onDelete method
                if (BuildConfig.DEBUG && taskId == 0) {
                    throw new AssertionError("TaskId is zero");
                }
                getContentResolver().delete(TasksContract.buildTaskUri(taskId), null, null);
                break;
            case DIALOG_ID_CANCEL_EDIT:
                // no action required, user can continue editing
                break;
            case DIALOG_ID_CANCEL_EDIT_UP:
                break;
        }
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResult: starts");
        switch (dialogId) {
            case DIALOG_ID_DELETE:
                // no action required.
                break;
            case DIALOG_ID_CANCEL_EDIT_UP:
            case DIALOG_ID_CANCEL_EDIT:
                // if we are editing, remove the fragment, otherwise close the app
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.task_details_container);
                if (fragment != null){
                    // we were editing
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                    if(mTwoPane){
                        // In landscape, quit only if the back button was used
                        if (dialogId == DIALOG_ID_CANCEL_EDIT){
                            finish();
                        }
                    } else {
                        // hide the container in single pane mode and make sure the left hand container is visible
                        View addEditLayout = findViewById(R.id.task_details_container);
                        View mainFragment = findViewById(R.id.fragment);
                        // We just remove the editing fragment so hide it
                        addEditLayout.setVisibility(View.GONE);
                        mainFragment.setVisibility(View.VISIBLE); // make sure main is visible
                    }
                } else {
                    // should exit regardless of orientation.
                    finish();
                    break;
                }
        }
    }

    // users press background, then nothing happens
    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: starts");
    }

    @Override
    public void onBackPressed() {
        // this function where we ask if user wants to discard edit should only occurs in two pane mode
        Log.d(TAG, "onBackPressed: starts");
//        super.onBackPressed(); // commenting this out stops the back button from working.
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditActivityFragment fragment = (AddEditActivityFragment) fragmentManager.findFragmentById(R.id.task_details_container);
        if (fragment == null || fragment.canClose()) { // canClose is implemented by ourselves.
            // if not in two pane mode (fragment not displayed)
            super.onBackPressed();
        } else {
            // pop up dialog to confirm to quit editing
            showConfirmationDialog(DIALOG_ID_CANCEL_EDIT);
//            AppDialog dialog = new AppDialog();
//            Bundle args = new Bundle();
//            args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL_EDIT);
//            args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.cancelEditDiag_message));
//            args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.cancelEditDiag_positive_caption);
//            args.putInt(AppDialog.DIALOG_NEGATIVE_RID, R.string.cancelEditDiag_negative_caption);
//
//            dialog.setArguments(args);
//            dialog.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.d(TAG, "onAttachFragment: called, fragment is " + fragment.toString());
        super.onAttachFragment(fragment);
    }

    private void showConfirmationDialog(int dialogId){
        // pop up dialog to confirm to quit editing
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID, dialogId);
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.cancelEditDiag_message));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.cancelEditDiag_positive_caption);
        args.putInt(AppDialog.DIALOG_NEGATIVE_RID, R.string.cancelEditDiag_negative_caption);

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onTaskLongClick(@NonNull Task task) {
        // Required to satisfy the interface
    }


}
