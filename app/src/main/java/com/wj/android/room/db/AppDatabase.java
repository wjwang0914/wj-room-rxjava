package com.wj.android.room.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wj.android.room.db.dao.UserDao;
import com.wj.android.room.db.entiy.User;

/**
 * 作者：wangwnejie on 2018/8/6 11:30
 * 邮箱：wangwenjie1303@stnts.com
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "room-database-name";

    private volatile static AppDatabase sInstance;

    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.i("AppDatabase", "database create success");
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    Log.i("AppDatabase", "database open success");
                                }
                            })
                            .build();
                }
            }
        }
        return sInstance;
    }
}
