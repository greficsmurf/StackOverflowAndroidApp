package com.example.stackexchange.db.typeconverters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toMillis(date: Date) = date.time

    @TypeConverter
    fun fromMillis(date: Long) = Date().apply { time = date }
}