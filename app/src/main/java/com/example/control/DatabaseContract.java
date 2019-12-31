package com.example.control;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract(){}

    public static final class PercentsEntry implements BaseColumns{
        public static final String TABLE_NAME = "percentList";
        public static final String COLUMN_NAME = "value";
    }

}
