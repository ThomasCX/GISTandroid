package com.example.groupproject;

import android.net.Uri;
import android.provider.BaseColumns;

public class TaskContract {

    private TaskContract (){}

    public static final class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "Tasks";


        public static final String COLUMN_DESCRIPTION ="description";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_TIMESTAMP = "timestamp";





    }
}
