package com.example.control;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract(){}

    public static final class PercentsEntry implements BaseColumns{
        public static final String TABLE_NAME = "percentList";
        public static final String COLUMN_NAME = "value";
    }

    public static final class TimetableEntry implements  BaseColumns{
        public static final String TABLE_NAME = "timetableList";
        public static final String COLUMN_DAY = "day";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_MINUTE = "minute";
        public static final String COLUMN_VALUE = "value";
    }

}
