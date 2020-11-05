package com.example.stackexchange.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

abstract class BaseDbModel{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "created_at")
    var createdAt: Date? = null
    @ColumnInfo(name = "updated_at")
    var updatedAt: Date? = null
}