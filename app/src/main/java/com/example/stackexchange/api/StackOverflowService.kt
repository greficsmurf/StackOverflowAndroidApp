package com.example.stackexchange.api

import com.example.stackexchange.api.models.SearchQuestionsApi
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "QyiURHLOWXQQnbrscSYRgw(("
interface StackOverflowService {

    @GET("search/advanced?key=$API_KEY&site=stackoverflow")
    suspend fun getQuestions(@Query("q") q: String) : SearchQuestionsApi

    @GET("questions?key=$API_KEY&site=stackoverflow&order=desc")
    suspend fun getAllQuestions(@Query("pagesize") pageSize: Int, @Query("sort") sort: String) : SearchQuestionsApi

}