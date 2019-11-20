package com.themeapp.dbdao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.themeapp.dbdata.CommentDb;

import java.util.List;

@Dao
public interface CommentDbDao {
    @Insert
    void insertAll(CommentDb... commentDb);

    @Query("select * from CommentDb")
    List<CommentDb> getCommentFromSync();

    @Delete
    int delete(CommentDb commentDb);//参数可以是数组，集合，返回的是删除成功的条数

    @Update
    int update(CommentDb... commentDb);//参数可以是数组，集合，返回的是update成功的条数

    //根据字段查询
    @Query("SELECT * FROM CommentDb WHERE tid= :tid Order BY cid DESC")
    List<CommentDb> getvaluebytid(String tid);
}
