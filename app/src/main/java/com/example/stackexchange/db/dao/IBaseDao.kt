package com.example.stackexchange.db.dao

import androidx.room.*
import com.example.stackexchange.db.models.BaseDbModel
import kotlinx.coroutines.flow.Flow
import java.util.*


interface IBaseDao <T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: T)
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(data: List<T>)

    @Delete
    suspend fun delete(data: T)


}