package com.themeapp.dbdata;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class CommentDb {
    @PrimaryKey(autoGenerate = true)
    public int cid;
    public int tid;
    public String comment;
    public String time;

    public CommentDb() {
    }
    @Ignore
    public CommentDb( int tid, String comment, String time) {
        this.tid = tid;
        this.comment = comment;
        this.time = time;
    }
}
