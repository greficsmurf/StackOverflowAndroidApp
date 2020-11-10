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
import com.example.stackexchange.utils.timeElapsed
import java.util.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PagedSearchQuestionsRemoteMediator @Inject constructor(
    private val stackOverflowService: StackOverflowService,
    private val hotQuestionDao: HotQuestionDao,
    private val weekQuestionDao: WeekQuestionDao,
    private val monthQuestionDao: MonthQuestionDao
) : BaseRemoteMediator<BaseSearchQuestionDb>() {

    var questionSort: QuestionSort = QuestionSort.Interesting()

    private val REFRESH_TIME = 1000 * 60 * 60 * 2L

    override suspend fun fetchApi(lastId: Long, pageSize: Int): List<BaseSearchQuestionDb>{
        val page = lastId / pageSize
        return stackOverflowService.getAllQuestions(pageSize, questionSort.name, page.plus(1).toInt()).toDomainModel().questions.map {
            it.toDbModel(questionSort.id)
        }
    }

    override suspend fun appendDb(list: List<BaseSearchQuestionDb>) = when(questionSort.id){
        QuestionSort.Hot().id -> hotQuestionDao.insertWithTimestamp(*list.map { it.toHot() }.toTypedArray())
        QuestionSort.Week().id -> weekQuestionDao.insertWithTimestamp(*list.map { it.toWeek() }.toTypedArray())
        QuestionSort.Month().id -> monthQuestionDao.insertWithTimestamp(*list.map { it.toMonth() }.toTypedArray())
        else -> Unit
    }

    override suspend fun refreshDb(list: List<BaseSearchQuestionDb>) = when(questionSort.id){
        QuestionSort.Hot().id -> hotQuestionDao.deleteAndInsert(list.map { it.toHot() })
        QuestionSort.Week().id -> weekQuestionDao.deleteAndInsert(list.map { it.toWeek() })
        QuestionSort.Month().id -> monthQuestionDao.deleteAndInsert(list.map { it.toMonth() })
        else -> Unit
    }

    override suspend fun shouldFetchApi(): Boolean {
        return (when(questionSort.id){
            QuestionSort.Hot().id -> hotQuestionDao.getFirstItem()?.updatedAt?.timeElapsed()
            QuestionSort.Week().id -> weekQuestionDao.getFirstItem()?.updatedAt?.timeElapsed()
            QuestionSort.Month().id -> monthQuestionDao.getFirstItem()?.updatedAt?.timeElapsed()
            else -> null
        } ?: return true) > REFRESH_TIME
    }

}