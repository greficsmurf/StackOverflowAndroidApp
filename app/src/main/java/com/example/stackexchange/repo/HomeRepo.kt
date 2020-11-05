package com.example.stackexchange.repo

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.models.SearchQuestionApi
import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.datasource.PagedSearchQuestionsDataSource
import com.example.stackexchange.datasource.PagedSearchQuestionsRemoteMediator
import com.example.stackexchange.db.dao.*
import com.example.stackexchange.db.models.BaseSearchQuestionDb
import com.example.stackexchange.db.models.HotSearchQuestionDb
import com.example.stackexchange.db.models.JoinSearchQuestionUserDb
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.domain.mappers.toDbModel
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.SearchQuestions
import com.example.stackexchange.vo.NetworkDatabaseResource
import com.example.stackexchange.vo.NetworkResource
import javax.inject.Inject
import javax.inject.Singleton


class HomeRepo
@Inject constructor(
        private val stackOverflowService: StackOverflowService,
        private val questionDao: SearchQuestionDao,
        private val hotQuestionDao: HotQuestionDao,
        private val weekQuestionDao: WeekQuestionDao,
        private val monthQuestionDao: MonthQuestionDao,

        private val questionsRemoteMediator: PagedSearchQuestionsRemoteMediator
) {

    fun getHomeQuestionsPagingSource(pageSize: Int,
                                     sort: QuestionSort = QuestionSort.Interesting()) = Pager(
        PagingConfig(prefetchDistance = 1, pageSize = pageSize, enablePlaceholders = true),
            remoteMediator = questionsRemoteMediator.apply { questionSort = sort }){
       when(sort.id){
           QuestionSort.Hot().id -> hotQuestionDao.getPagingSource()
           QuestionSort.Week().id -> weekQuestionDao.getPagingSource()
           QuestionSort.Month().id -> monthQuestionDao.getPagingSource()
           else -> hotQuestionDao.getPagingSource()
       } as PagingSource<Int, BaseSearchQuestionDb>
    }.liveData

}

