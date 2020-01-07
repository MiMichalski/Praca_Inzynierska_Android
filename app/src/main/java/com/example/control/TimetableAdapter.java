package com.example.control;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public TimetableAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;

    }


    public class TimetableViewHolder extends RecyclerView.ViewHolder {

        public TextView time;
        public TextView value;

        public TimetableViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.timetable_textView_time);
            value = itemView.findViewById(R.id.timetable_textView_value);
        }
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.timetable_item, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }

        int day = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.TimetableEntry.COLUMN_DAY)));
        int hour = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.TimetableEntry.COLUMN_HOUR)));
        int minute = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.TimetableEntry.COLUMN_MINUTE)));
        int value = Integer.parseInt(mCursor.getString(mCursor.getColumnIndex(DatabaseContract.TimetableEntry.COLUMN_VALUE)));
        long id = mCursor.getLong(mCursor.getColumnIndex(DatabaseContract.PercentsEntry._ID));

        String timeD = hour + ":" + minute;
        if (TimetableActivity.day == day) {
            holder.time.setText(timeD);
            holder.value.setText(value);
            holder.itemView.setTag(id);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }

}
