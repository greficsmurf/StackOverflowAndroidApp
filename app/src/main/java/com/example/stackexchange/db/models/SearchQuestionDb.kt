package com.example.stackexchange.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.stackexchange.api.models.UserApi
import com.squareup.moshi.Json
import java.util.*

@Entity(tableName = "search_question")
data class SearchQuestionDb(
        @PrimaryKey
        @ColumnInfo(name = "search_question_id")
        val searchQuestionId: Long,
        val tags: List<String>,
        @ForeignKey(entity = UserDb::class, parentColumns = ["search_question_id"], childColumns = ["owner_id"])
        @ColumnInfo(name = "owner_id")
        val ownerId: Long,
        @ColumnInfo(name = "is_answered")
        val isAnswered: Boolean,
        @ColumnInfo(name = "view_count")
        val viewCount: Long,
        @ColumnInfo(name = "answer_count")
        val answerCount: Long,
        val score: Int,
        val link: String,
        val title: String,
        val sortId: Int,
        val creationDate: Date,
        val lastActivityDate: Date
)