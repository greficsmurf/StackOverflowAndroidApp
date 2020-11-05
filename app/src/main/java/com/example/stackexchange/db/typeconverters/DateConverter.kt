package com.example.stackexchange.db.typeconverters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toMillis(date: Date?) = date?.time ?: System.currentTimeMillis()

    @TypeConverter
    fun fromMillis(date: Long?) = date?.let { Date(date) } ?: Date()
}