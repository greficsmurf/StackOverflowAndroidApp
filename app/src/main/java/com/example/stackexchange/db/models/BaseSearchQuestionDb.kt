package com.example.stackexchange.db.models

abstract class BaseSearchQuestionDb : BaseDbModel(), IBaseSearchQuestionDb{
    fun toHot() = HotSearchQuestionDb(
            searchQuestionId,
            tags,
            ownerId,
            isAnswered,
            viewCount,
            answerCount,
            score,
            questionLink,
            title,
            sortId,
            creationDate,
            lastActivityDate
    )

    fun toWeek() = WeekSearchQuestionDb(
            searchQuestionId,
            tags,
            ownerId,
            isAnswered,
            viewCount,
            answerCount,
            score,
            questionLink,
            title,
            sortId,
            creationDate,
            lastActivityDate
    )

    fun toMonth() = MonthSearchQuestionDb(
            searchQuestionId,
            tags,
            ownerId,
            isAnswered,
            viewCount,
            answerCount,
            score,
            questionLink,
            title,
            sortId,
            creationDate,
            lastActivityDate
    )
}