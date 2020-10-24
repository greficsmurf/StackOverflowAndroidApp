package com.example.stackexchange.db.typeconverters

import androidx.room.TypeConverter
import com.beust.klaxon.Klaxon

class StringListConverter {
    @TypeConverter
    fun toJsonList(list: List<String>) = Klaxon().toJsonString(list)

    @TypeConverter
    fun fromJsonList(string: String): List<String>? = Klaxon().parseArray(string)
}