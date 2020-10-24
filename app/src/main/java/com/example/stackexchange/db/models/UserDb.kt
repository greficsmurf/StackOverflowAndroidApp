package com.example.stackexchange.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "user")
data class UserDb(
        @PrimaryKey
        @ColumnInfo(name = "user_id")
        val userId: Long,
        val reputation: Int = 0,
        @ColumnInfo(name = "user_type")
        val userType: String,
        @ColumnInfo(name = "profile_image")
        val profileImage: String,
        @ColumnInfo(name = "display_name")
        val displayName: String,
        @ColumnInfo(name = "user_link")
        val link: String
)