package com.example.stackexchange.vo

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.lang.Exception

abstract class DatabaseResource<DbT, DomainT>{
    private val result = flow<Resource<DomainT>> {
        emit(Resource.loading(null))
        try{
            fetch().collect { dbRes ->
                emit(Resource.loaded(toDomainModel(dbRes)))
            }
        }catch (e: Exception){
            emit(Resource.failed(null))
        }
    }

    fun asFlow() = result
    fun asLiveData() = result.asLiveData()

    abstract suspend fun fetch(): Flow<DbT>
    abstract fun toDomainModel(data: DbT): DomainT
}