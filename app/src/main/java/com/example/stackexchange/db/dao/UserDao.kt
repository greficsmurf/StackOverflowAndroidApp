package com.example.stackexchange.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackexchange.db.models.UserDb
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao : BaseDao<UserDb>() {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    abstract suspend fun insert(list: List<UserDb>)

    @Query("SELECT * FROM user WHERE user_id=:userId")
    abstract fun getById(userId: Long): Flow<UserDb>

    @Query("SELECT * FROM user")
    abstract suspend fun get(): List<UserDb>
}