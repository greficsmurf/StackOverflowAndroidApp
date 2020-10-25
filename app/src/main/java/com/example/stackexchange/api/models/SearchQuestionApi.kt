package com.example.stackexchange.api.models

import com.squareup.moshi.Json
import java.util.*

data class SearchQuestionApi(
        val tags: List<String>,
        val owner: UserApi,
        @Json(name = "is_answered")
        val isAnswered: Boolean,
        @Json(name = "view_count")
        val viewCount: Long,
        @Json(name = "answer_count")
        val answerCount: Long,
        val score: Int,
        @Json(name = "question_id")
        val questionId: Long,
        val link: String,
        val title: String,
        @Json(name = "creation_date")
        val creationDate: Date,
        @Json(name = "last_activity_date")
        val lastActivityDate: Date
)