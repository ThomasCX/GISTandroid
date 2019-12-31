package com.example.groupproject;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddTask extends Fragment {
    private static final String TAG ="AddTask";
    private TextView edittask;
    private Button addbutton, radiobtn1,radiobtn2,radiobtn3;
    private CustomAdapter adapter;
    private SQLiteDatabase sqLiteDatabase;
    private int priorityCount;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.add_task,container,false);

        ((RadioButton) view.findViewById(R.id.rButton1)).setChecked(true);
        priorityCount = 1;


        SqlHelper dbHelper= new SqlHelper(getActivity());
        sqLiteDatabase= dbHelper.getWritableDatabase();
        adapter= new CustomAdapter(getActivity(),getAllItems());

        edittask= (TextView) view.findViewById(R.id.edittask);
        addbutton= (Button) view.findViewById(R.id.addButton);

        radiobtn1= (Button) view.findViewById(R.id.rButton1);
        radiobtn2= (Button) view.findViewById(R.id.rButton2);
        radiobtn3= (Button) view.findViewById(R.id.rButton3);

        radiobtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityCount=1;
            }
        });
        radiobtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityCount=2;
            }
        });
        radiobtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priorityCount=3;
            }
        });






        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
                Toast.makeText(getActivity(), "Item add", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);

            }
        });


        return view;
    }

    private  void addItem(){

        if (edittask.getText().toString().trim().length()== 0 ){
            return;
        }

        String desc = edittask.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION,desc);
        cv.put(TaskContract.TaskEntry.COLUMN_PRIORITY,priorityCount);

        sqLiteDatabase.insert(TaskContract.TaskEntry.TABLE_NAME,null,cv);
        adapter.swapCursor(getAllItems());


    }
    private Cursor getAllItems(){

        return sqLiteDatabase.query(TaskContract.TaskEntry.TABLE_NAME,null,null,null,
                null,null, TaskContract.TaskEntry.COLUMN_TIMESTAMP + " DESC");
    }



}
