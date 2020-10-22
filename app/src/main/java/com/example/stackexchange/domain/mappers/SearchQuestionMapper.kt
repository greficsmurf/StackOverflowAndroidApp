package com.example.stackexchange.domain.mappers

import com.example.stackexchange.api.models.SearchQuestionApi
import com.example.stackexchange.domain.models.SearchQuestion

fun SearchQuestionApi.toDomainModel() = SearchQuestion(
        tags,
        owner,
        isAnswered,
        viewCount,
        answerCount,
        score,
        questionId,
        link,
        title
)

