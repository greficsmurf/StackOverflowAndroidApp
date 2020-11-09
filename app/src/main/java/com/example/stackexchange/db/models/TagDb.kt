package com.example.stackexchange.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagDb(
        @ColumnInfo(name = "has_synonyms")
        val hasSynonyms: Boolean = false,
        @ColumnInfo(name = "is_moderator_only")
        val isModeratorOnly: Boolean = false,
        @ColumnInfo(name = "is_required")
        val isRequired: Boolean = false,
        val count: Long = 0,
        @PrimaryKey
        val name: String = "unrecognized tag"
) : BaseDbModel()