package com.example.stackexchange.api.adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    @FromJson
    fun toDate(unixTimeStamp: String) : Date{
        return Date().apply {
            time = unixTimeStamp.toLong()
        }
    }

    @ToJson
    fun fromDate(date: Date): String{
        return date.time.toString()
    }
}