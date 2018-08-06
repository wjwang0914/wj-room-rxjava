package com.wj.android.room.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wj.android.room.db.entiy.User;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * 作者：wangwnejie on 2018/8/6 11:31
 * 邮箱：wangwenjie1303@stnts.com
 */
@Dao
public interface UserDao {

    @Query("select * from user")
    Maybe<List<User>> getAll();//Flowable若数据库中用户信息被更新了，Flowable就会自动发射事件，允许你根据更新的数据来更新UI界面

    @Query("select * from user where id = :uid")
    List<User> loadAllById(int uid);

    @Insert
    void insertUser(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("delete from user")
    void delteAll();

}
