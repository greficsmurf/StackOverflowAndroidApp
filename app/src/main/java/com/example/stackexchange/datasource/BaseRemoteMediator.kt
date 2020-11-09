package com.example.stackexchange.datasource

import androidx.paging.*
import com.example.stackexchange.db.models.BaseDbModel
import com.example.stackexchange.db.models.SearchQuestionDb
import com.example.stackexchange.db.models.UserDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception

@ExperimentalPagingApi
abstract class BaseRemoteMediator <T> : RemoteMediator<Int, T>() where T: BaseDbModel{

    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        try {
            val lastItemId = when(loadType){
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                    lastItem.id
                }
            }

            var data = listOf<T>()

            lastItemId?.let { lastId ->
                data = fetchApi(lastId, state.config.pageSize)
                appendDb(data)
            } ?: let {
                data = fetchApi(pageSize = state.config.initialLoadSize + state.config.pageSize)
                refreshDb(data)
            }

            return MediatorResult.Success(data.isNullOrEmpty())

        }catch (e: Exception){
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    /**
     * lastId is 0 if first page or refresh requested
     */
    abstract suspend fun fetchApi(lastId: Long = 0, pageSize: Int): List<T>
    abstract suspend fun appendDb(list: List<T>)
    abstract suspend fun refreshDb(list: List<T>)
}