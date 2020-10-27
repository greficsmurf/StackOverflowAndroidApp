package com.example.stackexchange.api

import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.api.models.UserApi
import com.example.stackexchange.api.models.UsersApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "QyiURHLOWXQQnbrscSYRgw(("
interface StackOverflowService {
    @GET("search/advanced?key=$API_KEY&site=stackoverflow")
    suspend fun getQuestions(@Query("q") q: String,
                             @Query("page") page: Int = 1,
                             @Query("pagesize") pageSize: Int = 20) : SearchQuestionsApi

    @GET("questions?key=$API_KEY&site=stackoverflow&order=desc")
    suspend fun getAllQuestions(@Query("pagesize") pageSize: Int,
                                @Query("sort") sort: String,
                                @Query("page") page: Int = 1) : SearchQuestionsApi

    @GET("users/{id}?key=$API_KEY&site=stackoverflow")
    suspend fun getUser(@Path("id") id: Long) : UsersApi

    @GET("me?key=$API_KEY&site=stackoverflow")
    suspend fun getAuthUser(@Query("access_token") token: String): UsersApi
}