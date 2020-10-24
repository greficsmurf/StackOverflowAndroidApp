package com.example.stackexchange.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stackexchange.db.models.UserDb

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<UserDb>)

    @Query("SELECT * FROM user WHERE user_id=:userId")
    suspend fun getById(userId: Long): UserDb

}