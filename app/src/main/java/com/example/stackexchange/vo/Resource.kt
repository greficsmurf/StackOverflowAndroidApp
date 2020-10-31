package com.example.stackexchange.vo

import com.example.stackexchange.interfaces.ResourceCallback

data class Resource<T>(
        val data: T?,
        val status: ResourceStatus,
        val msg: String,
        val callback: ResourceCallback?
){
    companion object{
        fun <T> loading(data: T?, callback: ResourceCallback? = null, msg: String = "") = Resource(
                data,
                ResourceStatus.LOADING,
                msg,
                callback
        )
        fun <T> loaded(data: T?, callback: ResourceCallback? = null, msg: String = "") = Resource(
                data,
                ResourceStatus.LOADED,
                msg,
                callback
        )
        fun <T> failed(data: T?, callback: ResourceCallback? = null, msg: String = "") = Resource(
                data,
                ResourceStatus.FAILED,
                msg,
                callback
        )
        fun <T> authFailed(data: T?, callback: ResourceCallback? = null, msg: String = "") = Resource(
                data,
                ResourceStatus.AUTH_REJECTED,
                msg,
                callback
        )
    }
}