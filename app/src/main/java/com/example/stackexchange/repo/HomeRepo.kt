package com.example.stackexchange.repo

import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestions
import com.example.stackexchange.vo.NetworkResource
import javax.inject.Inject

class HomeRepo @Inject constructor(
        private val stackOverflowService: StackOverflowService
) {

    fun getAllQuestions(pageSize: Int, sort: QuestionSort = QuestionSort.Interesting()) = object : NetworkResource<SearchQuestionsApi, SearchQuestions>(){
        override suspend fun fetch() = stackOverflowService.getAllQuestions(pageSize, sort.name)
        override fun toDomainModel(data: SearchQuestionsApi) = data.toDomainModel()
    }.asFlow()
}