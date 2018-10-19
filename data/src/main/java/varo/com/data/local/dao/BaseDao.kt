package varo.com.data.local.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<T>)

    @Update
    fun update(obj: T)

    @Update
    fun updateAll(list: List<T>)

    @Delete
    fun delete(obj: T)

    @Delete
    fun deleteAll(list: List<T>)
}