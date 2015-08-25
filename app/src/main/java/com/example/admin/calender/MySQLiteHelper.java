package com.example.admin.calender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Admin on 8/22/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CALENDER = "calender";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECT_NAME = "subject_name";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ROOM_NO = "room_no";

    private static final String DATABASE_NAME = "calender.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statemnet
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CALENDER + "(" + COLUMN_ID
            + " integer primary key autoincrement, " +  COLUMN_SUBJECT_NAME
            + " text not null, " + COLUMN_SUBJECT_NAME
            + " text not null, " + COLUMN_TIME
            + " text not null, " + COLUMN_ROOM_NO
            + " text not null); ";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CALENDER);
        onCreate(db);
    }
}
