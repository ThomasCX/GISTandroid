package com.example.groupproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ViewTask extends Fragment {
    RecyclerView recyclerView;
    private SQLiteDatabase sqLiteDatabase;
    private CustomAdapter adapter;
    final ArrayList<String> list = new ArrayList<>();

    public ViewTask() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.view_task,container,false);


        return view;
    }

    public void onViewCreated(View view, Bundle SavedInstanceState) {
        SqlHelper dbHelper= new SqlHelper(getActivity());
        sqLiteDatabase= dbHelper.getReadableDatabase();
        recyclerView= view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CustomAdapter(getActivity(),getAllItems());
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    removeItem((long) viewHolder.itemView.getTag());
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red))
                        .addActionIcon(R.drawable.ic_delete)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);
    }
    private void removeItem(long id){
        sqLiteDatabase.delete(TaskContract.TaskEntry.TABLE_NAME, TaskContract.TaskEntry._ID + "=" + id,null);
        adapter.swapCursor(getAllItems());
    }

    private   Cursor getAllItems(){
        Cursor cursor= sqLiteDatabase.query(TaskContract.TaskEntry.TABLE_NAME,null,null,null,
                null,null, TaskContract.TaskEntry.COLUMN_TIMESTAMP + " DESC");

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            list.add(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION))); //add the item
            cursor.moveToNext();
        }
        System.out.println(list);


//       cursor.close();
//       sqLiteDatabase.close();
       return cursor;


    }




}
