package com.example.groupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.example.groupproject.TaskContract.*;

import androidx.annotation.Nullable;

public class SqlHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="organizer.db";

    public SqlHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       final String createToDOListTable = "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" + TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +  TaskEntry.COLUMN_DESCRIPTION +
               " TEXT NOT NULL, " + TaskEntry.COLUMN_PRIORITY + " INTEGER NOT NULL, " + TaskEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + ");";

       db.execSQL(createToDOListTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME);
            onCreate(db);
    }
}
