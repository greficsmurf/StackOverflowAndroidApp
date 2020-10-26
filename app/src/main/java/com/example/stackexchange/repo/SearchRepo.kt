package com.example.stackexchange.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.datasource.PagedSearchQuestionsDataSource
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.SearchQuestions
import com.example.stackexchange.vo.NetworkResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepo @Inject constructor(
        private val stackOverflowService: StackOverflowService
) {

    fun searchQuestions(title: String,
                        sort: QuestionSort = QuestionSort.Interesting()) = object : NetworkResource<SearchQuestionsApi, SearchQuestions>(){
        override suspend fun fetch() = stackOverflowService.getQuestions(title)

        override fun toDomainModel(data: SearchQuestionsApi) = data.toDomainModel()
    }.asFlow()

    fun getSearchedQuestionsDataSource(
        title: String, pageSize: Int, sort: QuestionSort = QuestionSort.Interesting()
    ) : LiveData<PagingData<SearchQuestion>> = Pager(PagingConfig(pageSize = pageSize)){
        PagedSearchQuestionsDataSource(
            stackOverflowService,
            sort,
            title
        )
    }.liveData

}