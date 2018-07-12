package benny.dev.tasktimer;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;

public class AppDialog extends AppCompatDialogFragment{
    private static final String TAG = "AppDialog";

    public static final String DIALOG_ID = "id";
    public static final String DIALOG_MESSAGE = "message";
    public static final String DIALOG_POSITIVE_RID = "positive_rid";
    public static final String DIALOG_NEGATIVE_RID = "negative_rid";

    /**
     * The dialog's callback interface to notify user's selected results and implement methods for all events (e.g delete, confirmed...
     * , is not a good way to do this because you may be have to adjust interface constantly and
     * all activities that may not use all methods have to implement the methods that they don't use
     */
    interface DialogEvents {
        void onPositiveDialogResult(int dialogId, Bundle args);
        void onNegativeDialogResult(int dialogId, Bundle args);
        void onDialogCancelled(int dialogId);
    }

    private DialogEvents mDialogEvents;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: starts, activity is " + context.toString());
        super.onAttach(context);

        // Activities containing this fragment must implement its callbacks.
        if(!(context instanceof DialogEvents)){
            throw new ClassCastException(context.toString() + " must implement AppDialog.DialogEvent interface");
        }

        mDialogEvents = (DialogEvents) context;
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: starts");
        super.onDetach();
        mDialogEvents = null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: starts");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Bundle arguments = getArguments();

        final int dialogId;
        String messageString;
        int positiveStringId;
        int negativeStringId;

        // extract arguments from bundle
        if(arguments != null){
            dialogId = arguments.getInt(DIALOG_ID);
            messageString = arguments.getString(DIALOG_MESSAGE);

            if(dialogId == 0 || messageString == null){
                throw new IllegalArgumentException("DIALOG_ID and/or DIALOG_MESSAGE not present in the bundle");
            }

            positiveStringId = arguments.getInt(DIALOG_POSITIVE_RID);
            negativeStringId = arguments.getInt(DIALOG_NEGATIVE_RID);
            if(positiveStringId == 0){
                positiveStringId = R.string.OK;
            }
            if(negativeStringId == 0){
                negativeStringId = R.string.cancel;
            }
        } else {
            throw new IllegalArgumentException("Must pass DIALOG_ID and DIALOG_MESSAGE in the bundle");
        }

        // Dialog popup box message set with button
        builder.setMessage(messageString)
                .setPositiveButton(positiveStringId, new DialogInterface.OnClickListener() { // can accept either string or int ids
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // callback positive result method
                        if(mDialogEvents != null){
                            mDialogEvents.onPositiveDialogResult(dialogId, arguments);
                        }
                    }
                })
                .setNegativeButton(negativeStringId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // callback negative result method
                        if(mDialogEvents != null) {
                            mDialogEvents.onNegativeDialogResult(dialogId, arguments);
                        }
                    }
                });
        return builder.create();
    }

    // if user press back button, Android calls the onCancel methods.
    @Override
    public void onCancel(DialogInterface dialog) {
        Log.d(TAG, "onCancel: starts");
        if(mDialogEvents != null){
            int dialogId = getArguments().getInt(DIALOG_ID);
            mDialogEvents.onDialogCancelled(dialogId); // don't pass bundle method cause not useful.
        }

    }
}
