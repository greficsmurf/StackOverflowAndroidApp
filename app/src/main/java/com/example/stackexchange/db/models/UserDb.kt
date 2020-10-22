package com.example.stackexchange.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDb(
        @PrimaryKey
        val id: Long
)