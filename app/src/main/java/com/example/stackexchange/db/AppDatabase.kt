package com.example.stackexchange.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stackexchange.db.models.UserDb

@Database(
        entities = [
            UserDb::class
        ],
        version = 1
)
abstract class AppDatabase : RoomDatabase()