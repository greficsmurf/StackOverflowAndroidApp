package com.example.stackexchange.datasource

import androidx.paging.PagingSource
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion

abstract class BaseDataSource <T>  : PagingSource<Int, T>() where T: Any {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> = try {

        val nextPage = params.key ?: 1
        val prevKey = if(nextPage == 1) null else nextPage - 1
        val data = fetchApi(nextPage, params.loadSize)

        if(data.isEmpty())
            LoadResult.Page(data, prevKey, null)
        else
            LoadResult.Page(data, prevKey, nextPage + 1)

    }catch (e: Exception){
        e.printStackTrace()
        LoadResult.Error(e)
    }

    abstract suspend fun fetchApi(page: Int, loadSize: Int): List<T>
}