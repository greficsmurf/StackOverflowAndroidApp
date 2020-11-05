package com.example.stackexchange.db.models

import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import java.util.*

interface IBaseSearchQuestionDb{
    val searchQuestionId: Long
    val tags: List<String>
    val ownerId: Long
    val isAnswered: Boolean
    val viewCount: Long
    val answerCount: Long
    val score: Int
    val questionLink: String
    val title: String
    val sortId: Int
    val creationDate: Date
    val lastActivityDate: Date
}