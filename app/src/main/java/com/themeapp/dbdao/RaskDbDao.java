package com.themeapp.dbdao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.themeapp.dbdata.TaskDb;

import java.util.List;

@Dao
public interface RaskDbDao {
    @Insert
    void insertAll(TaskDb... taskdb);

    @Query("select * from TaskDb")
    List<TaskDb> getTaskFromSync();

    @Delete
    int delete(TaskDb taskDb);//参数可以是数组，集合，返回的是删除成功的条数

    @Update
    int update(TaskDb... taskDb);//参数可以是数组，集合，返回的是update成功的条数

    //根据字段查询
    @Query("SELECT * FROM TaskDb WHERE title= :title")
    List<TaskDb> getvaluebyttitle(String title);
    //根据字段查询
    @Query("SELECT * FROM TaskDb WHERE tid= :tid")
    TaskDb getvaluebytid(int tid);

    //根据字段查询
    @Query("SELECT * FROM TaskDb WHERE isstudy = 1")
    List<TaskDb> getvaluebytisstudy();
}
