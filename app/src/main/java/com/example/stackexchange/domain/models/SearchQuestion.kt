package com.example.stackexchange.domain.models

import com.example.stackexchange.api.models.UserApi
import com.squareup.moshi.Json

data class SearchQuestion(
        val tags: List<String>,
        val owner: UserApi,
        val isAnswered: Boolean,
        val viewCount: Long,
        val answerCount: Long,
        val score: Int,
        val questionId: Long,
        val link: String,
        val title: String
)