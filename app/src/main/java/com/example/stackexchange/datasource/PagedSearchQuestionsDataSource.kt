package com.example.stackexchange.datasource

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.db.AppDatabase
import com.example.stackexchange.db.dao.SearchQuestionDao
import com.example.stackexchange.db.dao.UserDao
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.di.AppComponent
import com.example.stackexchange.di.AppInjector
import com.example.stackexchange.di.DaggerAppComponent
import com.example.stackexchange.di.Injectable
import com.example.stackexchange.domain.mappers.toDbModel
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.repo.QuestionSort
import com.example.stackexchange.vo.Resource
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject



class PagedSearchQuestionsDataSource(
    private val stackOverflowService: StackOverflowService,
    private val questionSort: QuestionSort = QuestionSort.Interesting(),
    private val searchString: String? = null
) : PagingSource<Int, SearchQuestion>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchQuestion> = try {
        val nextPage = params.key ?: 1
        val prevKey = if(nextPage == 1) null else nextPage - 1
        val data = if(searchString == null){
            stackOverflowService.getAllQuestions(params.loadSize, questionSort.name, nextPage).questionsApi
        }else{
            stackOverflowService.getQuestions(searchString,params.key ?: 1).questionsApi
        }.map { it.toDomainModel() }

        if(data.isEmpty())
            LoadResult.Page(data, prevKey, null)
        else
            LoadResult.Page(data, prevKey, nextPage + 1)
    }catch (e: Exception){
        e.printStackTrace()
        LoadResult.Error(e)
    }

}