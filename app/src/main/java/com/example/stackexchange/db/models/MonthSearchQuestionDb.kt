package com.example.stackexchange.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.*

@Entity(tableName = "month_search_question")
data class MonthSearchQuestionDb(
        @ColumnInfo(name = "search_question_id")
        override val searchQuestionId: Long,
        override val tags: List<String>,
        @ForeignKey(entity = UserDb::class, parentColumns = ["search_question_id"], childColumns = ["owner_id"])
        @ColumnInfo(name = "owner_id")
        override val ownerId: Long,
        @ColumnInfo(name = "is_answered")
        override val isAnswered: Boolean,
        @ColumnInfo(name = "view_count")
        override val viewCount: Long,
        @ColumnInfo(name = "answer_count")
        override val answerCount: Long,
        override val score: Int,
        @ColumnInfo(name = "question_link")
        override val questionLink: String,
        override val title: String,
        override val sortId: Int,
        @ColumnInfo(name = "creation_date")
        override val creationDate: Date,
        @ColumnInfo(name = "last_activity_date")
        override val lastActivityDate: Date
) : BaseSearchQuestionDb()