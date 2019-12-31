package com.example.groupproject;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.groupproject.R;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.TaskViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private ArrayList arrayList;

    public  CustomAdapter (Context context, Cursor cursor){
        mContext=context;
        mCursor= cursor;

    }

    public class  TaskViewHolder extends  RecyclerView.ViewHolder{

        public TextView description;
        public TextView priorityText;

        public TaskViewHolder( View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.taskDescription);
            priorityText = itemView.findViewById(R.id.priorityTextView);
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_task,parent,false);
        return  new TaskViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }

        String desc= mCursor.getString(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION));
        int priority= mCursor.getInt(mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY));
        long id = mCursor.getLong(mCursor.getColumnIndex(TaskContract.TaskEntry._ID));

        mCursor.moveToNext();

       holder.description.setText(desc);
       holder.priorityText.setText(String.valueOf(priority));
       holder.itemView.setTag(id);


        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityText.getBackground();
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);

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
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch(priority) {
            case 1: priorityColor = ContextCompat.getColor(mContext, R.color.red);
                break;
            case 2: priorityColor = ContextCompat.getColor(mContext, R.color.green);
                break;
            case 3: priorityColor = ContextCompat.getColor(mContext, R.color.blue);
                break;
            default: break;
        }
        return priorityColor;
    }


}
