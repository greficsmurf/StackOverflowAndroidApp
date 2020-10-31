package com.example.stackexchange.vo

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

abstract class NetworkResource<ApiT, DomainT> {
    private val result = flow<Resource<DomainT>> {
        emit(Resource.loading(null))
        try{
            emit(Resource.loaded(toDomainModel(fetch())))
        }catch (e: Exception){
            e.printStackTrace()
            emit(onError(e))
        }
    }

    fun asFlow() = result
    fun asLiveData() = result.asLiveData()

    abstract suspend fun fetch(): ApiT
    abstract fun toDomainModel(data: ApiT): DomainT

    open fun onError(e: Exception): Resource<DomainT> = Resource.failed(null)
}