package com.lwkandroid.lib.core.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

/**
 * @description: Room Dao层基础接口
 * @author: LWK
 * @date: 2020/7/3 14:25
 */
@Dao
public interface IRoomBaseDao<T>
{
    /**
     * 插入单条数据
     *
     * @param item 数据
     */
    @Insert
    void insertItem(T item);

    /**
     * 插入list数据
     *
     * @param items 数据
     */
    @Insert
    void insertItems(List<T> items);

    /**
     * 删除item
     *
     * @param item 数据
     */
    @Delete
    void deleteItem(T item);

    /**
     * 更新item
     *
     * @param item 数据
     */
    @Update
    void updateItem(T item);
}
