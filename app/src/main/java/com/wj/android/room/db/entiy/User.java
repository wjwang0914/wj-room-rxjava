package com.wj.android.room.db.entiy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * 作者：wangwnejie on 2018/8/6 11:31
 * 邮箱：wangwenjie1303@stnts.com
 */
@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)//自增长
    @ColumnInfo(name = "id")
    private int uid;
    @ColumnInfo(name = "name")
    private String userName;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
