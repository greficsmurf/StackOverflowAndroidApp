package com.example.stackexchange.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.stackexchange.db.models.TagDb
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TagDao : BaseDao<TagDb>() {

    @Query("SELECT * FROM tag")
    abstract fun getAll(): Flow<List<TagDb>>

}