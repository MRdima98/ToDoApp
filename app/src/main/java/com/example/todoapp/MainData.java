package com.example.todoapp;

//define table name

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "table_name")
public class MainData implements Serializable {
    //create id column
    @PrimaryKey(autoGenerate = true)

    private int ID;

    //create text column

    @ColumnInfo(name = "text")

    private String text;
    private int hours;
    private int minutes;

    //generate getter abd setter


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public int getHours(){return hours;}

    public int getMinutes(){return minutes;}

    public void setText(String text) { this.text = text; }

    public void setHours(int hours){this.hours=hours;}

    public void setMinutes(int minutes){this.minutes=minutes;}


}
