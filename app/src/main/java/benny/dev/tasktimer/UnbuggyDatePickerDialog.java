package benny.dev.tasktimer;

import android.app.DatePickerDialog;
import android.content.Context;

/**
 * To fix bug with date picker on older android ver
 *
 * Replacing tryNotifyDateSet() with nothing - this is a workaround for Android bug in API 4.x
 * @see <a href="https://code.google.com/p/android/issues/detail?id=34833">https://code.google.com/p/android/issues/detail?id=34833</a>
 *
 * Fix by Wojtek Jarosz.
 */
public class UnbuggyDatePickerDialog extends DatePickerDialog{

    public UnbuggyDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);
    }

    @Override
    protected void onStop() {
        // do nothing - do NOT call super method.
    }
}
