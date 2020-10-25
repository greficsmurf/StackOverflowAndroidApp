package com.example.stackexchange.vo

data class Resource<T>(
        val data: T?,
        val status: ResourceStatus,
        val msg: String
){
    companion object{
        fun <T> loading(data: T?, msg: String = "") = Resource(
                data,
                ResourceStatus.LOADING,
                msg
        )
        fun <T> loaded(data: T?, msg: String = "") = Resource(
                data,
                ResourceStatus.LOADED,
                msg
        )
        fun <T> failed(data: T?, msg: String = "") = Resource(
                data,
                ResourceStatus.FAILED,
                msg
        )
    }
}