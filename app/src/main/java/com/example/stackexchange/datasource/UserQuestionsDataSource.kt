package com.example.stackexchange.datasource

import androidx.paging.PagingSource
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.repo.QuestionSort
import timber.log.Timber
import java.lang.Exception

class UserQuestionsDataSource (private val stackOverflowService: StackOverflowService, private val userId: Long
) : PagingSource<Int, SearchQuestion>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchQuestion> = try {
        val nextPage = params.key ?: 1
        val data = stackOverflowService.getUserQuestions(userId, params.loadSize, nextPage).questionsApi.map { it.toDomainModel() }
        if(data.isEmpty())
            LoadResult.Page(data, if(nextPage == 1) null else nextPage - 1, null)
        else
            LoadResult.Page(data, if(nextPage == 1) null else nextPage - 1, nextPage + 1)
    }catch (e: Exception){
        Timber.d(e.message)
        LoadResult.Error(e)
    }

}