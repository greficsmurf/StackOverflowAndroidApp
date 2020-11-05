package com.example.stackexchange.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.stackexchange.vo.Resource
import java.util.*

fun <T> MutableLiveData<T>.forceRefresh(){
    this.value = this.value
}

fun getResourceByLoadStates(loadStates: CombinedLoadStates): Resource<Nothing>{
    when {
        loadStates.prepend is LoadState.Loading -> return Resource.loading(null)
        loadStates.append is LoadState.Loading -> return Resource.loading(null)
        loadStates.refresh is LoadState.Loading -> return Resource.loading(null)
    }

    when {
        loadStates.prepend is LoadState.Error -> return Resource.failed(null)
        loadStates.append is LoadState.Error -> return Resource.failed(null)
        loadStates.refresh is LoadState.Error -> return Resource.failed(null)
    }


    return Resource.loaded(null)
}

fun Long.toDate() = Date().apply { time = this@toDate }

fun Any?.isNull() = this == null