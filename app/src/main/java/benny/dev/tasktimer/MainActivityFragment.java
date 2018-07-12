package benny.dev.tasktimer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.InvalidParameterException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivityFragment";

    public static final int LOADER_ID = 0;

    private CursorRecyclerViewAdapter mAdapter; // add adapter reference

    public MainActivityFragment() {
        Log.d(TAG, "MainActivityFragment: starts");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // loader better initialised here
        Log.d(TAG, "onActivityCreated: starts");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this); // args always null in Android's Loader class. callback is which object will be handling the callback, which is usually the fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new CursorRecyclerViewAdapter(null); // pass null since we don't have data yet.
        recyclerView.setAdapter(mAdapter);
        Log.d(TAG, "onCreateView: returning");
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(TAG, "onCreateLoader: starts with id = " + id);
        String[] projection = {TasksContract.Columns._ID, TasksContract.Columns.TASKS_NAME,
                                TasksContract.Columns.TASKS_DESCRIPTION, TasksContract.Columns.TASKS_SORTORDER};
        String sortOrder = TasksContract.Columns.TASKS_SORTORDER + "," + TasksContract.Columns.TASKS_NAME;
        switch (id){
            case LOADER_ID:
                return new CursorLoader(getActivity(), TasksContract.CONTENT_URI, projection, null, null, sortOrder); // since we want all rows and columns, selection and selection args are null
                default:
                    throw new InvalidParameterException(TAG + " .OnCreateLoader called with invalid loader id " + id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // loader manager calls this to retrieve
        Log.d(TAG, "onLoadFinished: starts");
        mAdapter.swapCursor(data); // use adapter instead of the code below to get data. We get the data from adapter.
        int count = mAdapter.getItemCount();
//        if(data != null){
//            while (data.moveToNext()){
//                for(int i=0; i<data.getColumnCount(); i++){
//                    Log.d(TAG, "onLoadFinished: data is " + data.getColumnName(i) + " : " + data.getString(i));
//                }
//                Log.d(TAG, "onLoadFinished: +++++++++++++++++++");
//            }
//            count = data.getCount();
//            // we don't close the cursor because it doesn't belong to us but belongs to cursor loader. cursor loader needs this to know when there are chagnes to data.
//        }
        Log.d(TAG, "onLoadFinished: finished with count = " + count);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: starts");
        mAdapter.swapCursor(null); // now the adapter doesn't have the old reference anymore.
    }
}
