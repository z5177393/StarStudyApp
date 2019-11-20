package com.themeapp.dbdata;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TaskDb implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int tid;

    public String date;
    public String explanation;
    public String title;
    public String url;
    public String isstudy;

    public TaskDb() {
    }

    @Ignore
    public TaskDb( String date, String explanation, String title, String url, String isstudy) {
        this.date = date;
        this.explanation = explanation;
        this.title = title;
        this.url = url;
        this.isstudy = isstudy;
    }
}
