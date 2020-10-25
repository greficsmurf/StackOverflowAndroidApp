package com.example.stackexchange.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stackexchange.db.dao.SearchQuestionDao
import com.example.stackexchange.db.dao.UserDao
import com.example.stackexchange.db.models.JoinSearchQuestionUserDb
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.db.models.UserDb
import com.example.stackexchange.db.typeconverters.DateConverter
import com.example.stackexchange.db.typeconverters.StringListConverter

@Database(
        entities = [
            UserDb::class,
            SearchQuestionDb::class
        ],
        version = 1
)
@TypeConverters(StringListConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getSearchQuestionDao(): SearchQuestionDao
    abstract fun getUserDao(): UserDao

}