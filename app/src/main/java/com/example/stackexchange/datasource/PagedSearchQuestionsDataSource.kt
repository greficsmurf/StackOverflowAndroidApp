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
    private val searchString: String? = null,
    private val tags: List<String>
) : BaseDataSource<SearchQuestion>(){
    private val tagsStr = tags.joinToString(";")

    override suspend fun fetchApi(page: Int, loadSize: Int): List<SearchQuestion> = if(searchString == null){
        stackOverflowService.getAllQuestions(loadSize, questionSort.name, page).questionsApi
    }else{
        stackOverflowService.getQuestions(searchString,page, tags = tagsStr).questionsApi
    }.map { it.toDomainModel() }

}