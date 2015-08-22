package com.example.admin.calender;

/**
 * Created by Admin on 8/22/2015.
 */
public class Schedule {
    private long id;
    private String subject_name;
    private String room_no;
    private String time;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getSubjectName(){
        return subject_name;
    }

    public void setSubjectName(String subject_name){
        this.subject_name = subject_name;
    }

    public String getRoomNo(){
        return room_no;
    }

    public void setRoomNo(String room_no){
        this.room_no = room_no;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    // Will be used by Arrayadapter in the listview
    @Override
    public String toString(){
        //return comment;
        return "";
    }
}
