package com.themeapp.dbdao;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.themeapp.application.App;
import com.themeapp.dbdao.RaskDbDao;
import com.themeapp.dbdata.CommentDb;
import com.themeapp.dbdata.TaskDb;

//所有的entity注解的类，都得在这里声明
@Database(entities = {TaskDb.class, CommentDb.class},version =2)
public abstract class JavaDatabase extends RoomDatabase {

    //定义了几个Dao注解的类，这里就写几个抽象方法
    public abstract RaskDbDao raskDbDao();
    public abstract CommentDbDao commentDbDao();

    private static JavaDatabase javaDatabase;

    public static JavaDatabase instance(){
        if(javaDatabase==null){
            synchronized (JavaDatabase.class){
                if(javaDatabase==null){
                    javaDatabase= Room.databaseBuilder(App.context,JavaDatabase.class,"test").
                            addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    System.out.println("onCreate==========="+db.getVersion()+"==="+db.getPath());
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    System.out.println("onOpen==========="+db.getVersion()+"==="+db.getPath());
                                }
                            })
                            .allowMainThreadQueries()//允许在主线程查询数据
//                            .addMigrations(migration)//迁移数据库使用，下面会单独拿出来讲
                            .fallbackToDestructiveMigration()//迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                            .build();
                }
            }
        }
        return javaDatabase;
    }

}