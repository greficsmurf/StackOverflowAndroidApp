package com.example.stackexchange.domain.mappers

import com.example.stackexchange.api.models.SearchQuestionApi
import com.example.stackexchange.db.models.JoinSearchQuestionUserDb
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.User
import java.util.*

fun SearchQuestionApi.toDomainModel() = SearchQuestion(
        tags,
        owner.toDomainModel(),
        isAnswered,
        viewCount,
        answerCount,
        score,
        questionId,
        link,
        title,
        creationDate,
        lastActivityDate
)

fun SearchQuestionDb.toDomainModel(owner: User) = SearchQuestion(
        tags,
        owner,
        isAnswered,
        viewCount,
        answerCount,
        score,
        searchQuestionId,
        link,
        title,
        creationDate,
        lastActivityDate
)

fun SearchQuestion.toDbModel(sortId: Int = 0) = SearchQuestionDb(
        questionId,
        tags,
        owner.userId,
        isAnswered,
        viewCount,
        answerCount,
        score,
        link,
        title,
        sortId,
        creationDate,
        lastActivityDate
)

fun JoinSearchQuestionUserDb.toDomainModel() = SearchQuestion(
        searchQuestion.tags,
        user.toDomainModel(),
        searchQuestion.isAnswered,
        searchQuestion.viewCount,
        searchQuestion.answerCount,
        searchQuestion.score,
        searchQuestion.searchQuestionId,
        searchQuestion.link,
        searchQuestion.title,
        searchQuestion.creationDate,
        searchQuestion.lastActivityDate
)