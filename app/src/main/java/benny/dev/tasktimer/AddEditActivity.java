package benny.dev.tasktimer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class AddEditActivity extends AppCompatActivity implements AddEditActivityFragment.OnSaveClicked,
                                                                AppDialog.DialogEvents {

    private static final String TAG = "AddEditActivity";
    public static final int DIALOG_ID_CANCEL_EDIT = 1; // value doesn't matter as long as it is different from other constants in this class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get fragment manager to add fragment
        AddEditActivityFragment fragment = new AddEditActivityFragment();

        Bundle arguments = getIntent().getExtras();
//        arguments.putSerializable(Task.class.getSimpleName(), ge);
        fragment.setArguments(arguments);

        FragmentManager fragmentManager = getSupportFragmentManager();
        // FragmentTransaction queues up the changes and perform them
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment); // replace instead of add. Replace will work even if nothing existed.
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // home is actually arrow button on top
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: Home button pressed");
                AddEditActivityFragment fragment = (AddEditActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                if(fragment.canClose()){
                    return super.onOptionsItemSelected(item); // we don't want to show the dialog so let android handle this
                } else {
                    showConfirmationDialog();
                    return true; // indicate we are handling this.
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveClick() {
        finish(); // when you want the activity to finish
    }

    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: starts");
        // user continues editing so no action required.
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResult: starts");
        // need to exit
        finish();
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: starts");
        // if dialog cancelled, nothing to do
    }

    private void showConfirmationDialog(){
        // pop up dialog to confirm to quit editing
        AppDialog dialog = new AppDialog();
        Bundle args = new Bundle();
        args.putInt(AppDialog.DIALOG_ID, DIALOG_ID_CANCEL_EDIT);
        args.putString(AppDialog.DIALOG_MESSAGE, getString(R.string.cancelEditDiag_message));
        args.putInt(AppDialog.DIALOG_POSITIVE_RID, R.string.cancelEditDiag_positive_caption);
        args.putInt(AppDialog.DIALOG_NEGATIVE_RID, R.string.cancelEditDiag_negative_caption);

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), null);
    }

    @Override
    public void onBackPressed() {
        // this function where we ask if user wants to discard edit should only occurs in two pane mode
        Log.d(TAG, "onBackPressed: starts");
//        super.onBackPressed(); // commenting this out stops the back button from working.
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEditActivityFragment fragment = (AddEditActivityFragment) fragmentManager.findFragmentById(R.id.fragment); // fragment ID
        if (fragment.canClose()) { // canClose is implemented by ourselves.
            // if not in two pane mode (fragment not displayed)
            super.onBackPressed();
        } else {
            showConfirmationDialog();
        }
    }
}
