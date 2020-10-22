package com.example.stackexchange.domain.mappers

import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.domain.models.SearchQuestions

fun SearchQuestionsApi.toDomainModel() = SearchQuestions(
        questionsApi.map { it.toDomainModel() }
)