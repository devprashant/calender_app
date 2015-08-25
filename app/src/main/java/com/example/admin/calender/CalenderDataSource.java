package com.example.admin.calender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/22/2015.
 */
public class CalenderDataSource {

    //Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_SUBJECT_NAME,
            MySQLiteHelper.COLUMN_ROOM_NO,
            MySQLiteHelper.COLUMN_TIME};

    public CalenderDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createSchedule(String subject_name, String room_no, String time) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SUBJECT_NAME, subject_name);
        values.put(MySQLiteHelper.COLUMN_ROOM_NO, room_no);
        values.put(MySQLiteHelper.COLUMN_TIME, time);

        long insertId = database.insert(MySQLiteHelper.TABLE_CALENDER, null, values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CALENDER, allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Schedule newSchedule = cursorToComment(cursor);
        cursor.close();

    }

    public void deleteFullSchedule() {
        System.out.println("Deleting all previoes schedule");
        database.execSQL("DELETE * FROM " + MySQLiteHelper.TABLE_CALENDER);
    }

    public List<Schedule> getFullSchedule(){
        List<Schedule> fullSchedule = new ArrayList<Schedule>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_CALENDER, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Schedule singleSchedule = cursorToComment(cursor);
            fullSchedule.add(singleSchedule);
            cursor.moveToNext();
        }
        //make sure to close the cursor
        cursor.close();
        return fullSchedule;
    }

    private Schedule cursorToComment(Cursor cursor) {
        Schedule singleSchedule = new Schedule();
        singleSchedule.setId(cursor.getLong(0));
        singleSchedule.setSubjectName(cursor.getString(1));
        singleSchedule.setRoomNo(cursor.getString(3));
        singleSchedule.setTime(cursor.getString(2));
        return singleSchedule;
    }

}