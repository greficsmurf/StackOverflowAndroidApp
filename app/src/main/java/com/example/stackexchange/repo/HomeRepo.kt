package com.example.stackexchange.repo

import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.models.SearchQuestionApi
import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.db.dao.SearchQuestionDao
import com.example.stackexchange.db.dao.UserDao
import com.example.stackexchange.db.models.JoinSearchQuestionUserDb
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.domain.mappers.toDbModel
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.SearchQuestions
import com.example.stackexchange.vo.NetworkDatabaseResource
import com.example.stackexchange.vo.NetworkResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HomeRepo @Inject constructor(
        private val stackOverflowService: StackOverflowService,
        private val questionDao: SearchQuestionDao,
        private val userDao: UserDao
) {

    fun getAllQuestions(pageSize: Int, sort: QuestionSort = QuestionSort.Interesting()) = object : NetworkResource<SearchQuestionsApi, SearchQuestions>(){
        override suspend fun fetch() = stackOverflowService.getAllQuestions(pageSize, sort.name)
        override fun toDomainModel(data: SearchQuestionsApi) = data.toDomainModel()
    }.asFlow()

    fun getHomeQuestionsResource(pageSize: Int, sort: QuestionSort = QuestionSort.Interesting())
            = object : NetworkDatabaseResource<List<SearchQuestionApi>, List<JoinSearchQuestionUserDb>, List<SearchQuestion>>(){
        override suspend fun fetchDb() = questionDao.getSortedQuestionDb(sort.id)

        override fun toDomainModelDb(data: List<JoinSearchQuestionUserDb>) = data.map { it.toDomainModel() }

        override suspend fun fetchApi() = stackOverflowService.getAllQuestions(pageSize, sort.name).questionsApi

        override fun toDomainModelApi(data: List<SearchQuestionApi>) = data.map { it.toDomainModel() }

        override fun shouldFetchApi(data: List<JoinSearchQuestionUserDb>?) = data.isNullOrEmpty()

        override suspend fun onDbSave(data: List<SearchQuestion>) {
            userDao.insertAll(
                    data.map { it.owner.toDbModel() }
            )
            questionDao.insertAll(
                    data.map { it.toDbModel(sort.id) }
            )
        }


    }
}