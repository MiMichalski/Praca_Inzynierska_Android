package com.example.control;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DatabaseAdapter extends RecyclerView.Adapter<DatabaseAdapter.PercenViewHolder>{

    private Context mContext;
    private Cursor mCursor;

    public DatabaseAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;

    }


    public class PercenViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;

        public PercenViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textview_value_item);
        }
    }

    @NonNull
    @Override
    public PercenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.percent_item, parent, false);
        return new PercenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PercenViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(DatabaseContract.PercentsEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(DatabaseContract.PercentsEntry._ID));

        holder.nameText.setText(name);
        holder.itemView.setTag(id);
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
