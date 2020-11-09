package com.example.stackexchange.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stackexchange.db.dao.*
import com.example.stackexchange.db.models.*
import com.example.stackexchange.db.typeconverters.DateConverter
import com.example.stackexchange.db.typeconverters.StringListConverter

@Database(
        entities = [
            UserDb::class,
            SearchQuestionDb::class,
            HotSearchQuestionDb::class,
            WeekSearchQuestionDb::class,
            MonthSearchQuestionDb::class,
            TagDb::class
        ],
        version = 1
)
@TypeConverters(StringListConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getSearchQuestionDao(): SearchQuestionDao
    abstract fun getUserDao(): UserDao

    abstract fun getHotQuestionDao(): HotQuestionDao
    abstract fun getWeekQuestionDao(): WeekQuestionDao
    abstract fun getMonthQuestionDao(): MonthQuestionDao
    abstract fun getTagDao(): TagDao

}