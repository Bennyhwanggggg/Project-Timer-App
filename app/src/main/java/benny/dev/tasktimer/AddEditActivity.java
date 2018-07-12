package benny.dev.tasktimer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class AddEditActivity extends AppCompatActivity implements AddEditActivityFragment.OnSaveClicked{

    private static final String TAG = "AddEditActivity";

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
    public void onSaveClick() {
        finish(); // when you want the activity to finish
    }
}
