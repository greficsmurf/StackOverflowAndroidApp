package com.example.stackexchange.api.models

import com.squareup.moshi.Json

data class SearchQuestionsApi(
        @Json(name = "items")
        val questionsApi: List<SearchQuestionApi>
)