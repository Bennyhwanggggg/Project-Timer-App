package benny.dev.tasktimer;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;


public class DurationsRVAdapter extends RecyclerView.Adapter<DurationsRVAdapter.ViewHolder>{

    private Cursor mCursor;
    private final java.text.DateFormat mDateFormat; // module level so we don't keep instantiating in bindView

    public DurationsRVAdapter(Cursor cursor, Context context) {
        mCursor = cursor;
        mDateFormat = DateFormat.getDateFormat(context); // Uses locale
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_durations_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if ((mCursor != null) && (mCursor.getCount() != 0)) {
            if (!mCursor.moveToPosition(position)) {
                throw new IllegalStateException("Couldn't move cursor to position " + position);
            }
            String name = mCursor.getString(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_NAME));
            String description = mCursor.getString(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_DESCRIPTION));
            Long startTime = mCursor.getLong(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_START_TIME));
            long totalDuration = mCursor.getLong(mCursor.getColumnIndex(DurationsContract.Columns.DURATIONS_DURATION));

            holder.name.setText(name);
            if (holder.description != null) {    // Description is not present in portrait
                holder.description.setText(description);
            }

            String userDate = mDateFormat.format(startTime * 1000); // The database stores seconds, we need milliseconds
            String totalTime = formatDuration(totalDuration);

            holder.startDate.setText(userDate);
            holder.duration.setText(totalTime);
        }
    }

    private String formatDuration(long duration) {
        // duration is in seconds, convert to hours:minutes:seconds
        // (allowing for >24 hours - so we can't a time data type);
        long hours = duration / 3600;
        long remainder = duration - (hours * 3600);
        long minutes = remainder / 60;
        long seconds = remainder - (minutes * 60);

        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.
     * THe returned old Cursor is <em>not</em> closed.
     *
     * @param newCursor the new cursor to be used
     * @return Returns the previously set Cursor, or null if there wasn't one.
     * If the given new cursor is the same instance as the previously set cursor, null is also returned.
     */
    Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        final Cursor oldCursor = mCursor;
        mCursor = newCursor;
        // make the data refresh even if the back button is pressed
        if (newCursor != null) {
            // notify the observer about the new cursor.
            notifyDataSetChanged();
        } else {
            // notify the observer about the lack of a data set
            notifyItemRangeRemoved(0, getItemCount());
        }
        return oldCursor;
    }

    static class ViewHolder extends RecyclerView.ViewHolder { // Make class static to prevent memory leak
        TextView name;
        TextView description;
        TextView startDate;
        TextView duration;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.td_name);
            this.description = itemView.findViewById(R.id.td_description);
            this.startDate = itemView.findViewById(R.id.td_start);
            this.duration = itemView.findViewById(R.id.td_duration);

        }
    }
}
