package com.example.stackexchange.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.db.AppDatabase
import com.example.stackexchange.db.dao.*
import com.example.stackexchange.db.models.*
import com.example.stackexchange.domain.mappers.toDbModel
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.repo.QuestionSort
import com.example.stackexchange.utils.isNull
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PagedSearchQuestionsRemoteMediator @Inject constructor(
    private val stackOverflowService: StackOverflowService,
    private val hotQuestionDao: HotQuestionDao,
    private val weekQuestionDao: WeekQuestionDao,
    private val monthQuestionDao: MonthQuestionDao
) : BaseRemoteMediator<BaseSearchQuestionDb>() {

    var questionSort: QuestionSort = QuestionSort.Interesting()

    override suspend fun fetchApi(lastId: Long, pageSize: Int): List<BaseSearchQuestionDb>{
        val page = lastId / pageSize
        return stackOverflowService.getAllQuestions(pageSize, questionSort.name, page.plus(1).toInt()).toDomainModel().questions.map {
            it.toDbModel(questionSort.id)
        }
    }

    override suspend fun appendDb(list: List<BaseSearchQuestionDb>) = when(questionSort.id){
        QuestionSort.Hot().id -> hotQuestionDao.insert(list.map { it.toHot() })
        QuestionSort.Week().id -> weekQuestionDao.insert(list.map { it.toWeek() })
        QuestionSort.Month().id -> monthQuestionDao.insert(list.map { it.toMonth() })
        else -> Unit
    }

    override suspend fun refreshDb(list: List<BaseSearchQuestionDb>) = when(questionSort.id){
        QuestionSort.Hot().id -> hotQuestionDao.deleteAndInsert(list.map { it.toHot() })
        QuestionSort.Week().id -> weekQuestionDao.deleteAndInsert(list.map { it.toWeek() })
        QuestionSort.Month().id -> monthQuestionDao.deleteAndInsert(list.map { it.toMonth() })
        else -> Unit
    }

}