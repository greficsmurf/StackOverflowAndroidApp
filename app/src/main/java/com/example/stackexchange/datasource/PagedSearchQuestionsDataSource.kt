package com.example.stackexchange.datasource

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.repo.QuestionSort
import com.example.stackexchange.vo.Resource
import timber.log.Timber
import java.lang.Exception

class PagedSearchQuestionsDataSource(
    private val stackOverflowService: StackOverflowService,
    private val questionSort: QuestionSort = QuestionSort.Interesting(),
    private val searchString: String? = null
) : PagingSource<Int, SearchQuestion>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchQuestion> = try {
        val nextPage = params.key ?: 1
        val data = if(searchString == null){
            stackOverflowService.getAllQuestions(params.loadSize, questionSort.name, nextPage).questionsApi
        }else{
            stackOverflowService.getQuestions(searchString,params.key ?: 1).questionsApi
        }.map { it.toDomainModel() }

        LoadResult.Page(data, if(nextPage == 1) null else nextPage - 1, nextPage + 1)
    }catch (e: Exception){
        Timber.d(e.message)
        LoadResult.Error(e)
    }

}