package com.example.stackexchange.repo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.api.models.TagApi
import com.example.stackexchange.datasource.PagedSearchQuestionsDataSource
import com.example.stackexchange.datasource.PagedTagSearchQuestionsDataSource
import com.example.stackexchange.db.dao.TagDao
import com.example.stackexchange.db.models.TagDb
import com.example.stackexchange.domain.mappers.toDbModel
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.SearchQuestions
import com.example.stackexchange.domain.models.Tag
import com.example.stackexchange.vo.NetworkDatabaseResource
import com.example.stackexchange.vo.NetworkResource
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepo @Inject constructor(
        private val stackOverflowService: StackOverflowService,
        private val tagDao: TagDao
) {

    fun searchQuestions(title: String,
                        sort: QuestionSort = QuestionSort.Interesting()) = object : NetworkResource<SearchQuestionsApi, SearchQuestions>(){
        override suspend fun fetch() = stackOverflowService.getQuestions(title)

        override fun toDomainModel(data: SearchQuestionsApi) = data.toDomainModel()
    }.asFlow()

    fun getSearchedQuestionsDataSource(
        title: String, pageSize: Int, sort: QuestionSort = QuestionSort.Interesting(), tags: List<String>
    ) : LiveData<PagingData<SearchQuestion>> = Pager(PagingConfig(initialLoadSize = pageSize, pageSize = pageSize)){
        PagedSearchQuestionsDataSource(
            stackOverflowService,
            sort,
            title, tags
        )
    }.liveData

    fun getTagSearchQuestionsDataSource(
            tags: List<String>, pageSize: Int
    ) : LiveData<PagingData<SearchQuestion>> = Pager(PagingConfig(initialLoadSize = pageSize, pageSize = pageSize)){
        PagedTagSearchQuestionsDataSource(stackOverflowService, tags)
    }.liveData


    fun getPopularTags() = object : NetworkDatabaseResource<List<TagApi>, List<TagDb>, List<Tag>>(){
        override suspend fun fetchDb(): Flow<List<TagDb>> = tagDao.getAll()

        override fun toDomainModelDb(data: List<TagDb>): List<Tag> = data.map { it.toDomainModel() }
        override suspend fun fetchApi(): List<TagApi> = stackOverflowService.getPopularTags().data

        override fun toDomainModelApi(data: List<TagApi>): List<Tag> = data.map { it.toDomainModel() }

        override fun shouldFetchApi(data: List<TagDb>?): Boolean {
            if(data.isNullOrEmpty())
                return true

            val firstItemUpdateTime = data.first().updatedAt ?: return true
            if((Date().time - firstItemUpdateTime.time) <= (1000 * 60 * 60 * 24L))
                return false

            return true
        }

        override suspend fun onDbSave(data: List<Tag>) {
            tagDao.insertWithTimestamp(*data.map { it.toDbModel() }.toTypedArray())
        }
    }


}