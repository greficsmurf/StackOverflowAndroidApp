package com.example.stackexchange.datasource

import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion

class PagedTagSearchQuestionsDataSource(
        private val stackOverflowService: StackOverflowService,
        private val tags: List<String>
) : BaseDataSource<SearchQuestion>(){
    private val delimiter = ";"

    override suspend fun fetchApi(page: Int, loadSize: Int): List<SearchQuestion> = stackOverflowService.getTaggedQuestions(
            tags.joinToString(delimiter), page, loadSize
    ).toDomainModel().questions

}