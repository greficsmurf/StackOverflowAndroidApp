package com.example.stackexchange.api

import com.example.stackexchange.api.models.SearchQuestionsApi
import com.example.stackexchange.api.models.TagsApi
import com.example.stackexchange.api.models.UserApi
import com.example.stackexchange.api.models.UsersApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "QyiURHLOWXQQnbrscSYRgw(("
interface StackOverflowService {
    @GET("search/advanced?key=$API_KEY&site=stackoverflow&sort=relevance")
    suspend fun getQuestions(@Query("q") q: String,
                             @Query("page") page: Int = 1,
                             @Query("pagesize") pageSize: Int = 20,
                             @Query("tagged") tags: String = "") : SearchQuestionsApi

    @GET("questions?key=$API_KEY&site=stackoverflow&order=desc")
    suspend fun getAllQuestions(@Query("pagesize") pageSize: Int,
                                @Query("sort") sort: String,
                                @Query("page") page: Int = 1) : SearchQuestionsApi

    @GET("users/{id}?key=$API_KEY&site=stackoverflow")
    suspend fun getUser(@Path("id") id: Long) : UsersApi

    @GET("users/{id}/questions?key=$API_KEY&site=stackoverflow")
    suspend fun getUserQuestions(@Path("id") id: Long,
                                 @Query("pagesize") pageSize: Int,
                                 @Query("page") page: Int) : SearchQuestionsApi

    @GET("me?key=$API_KEY&site=stackoverflow")
    suspend fun getAuthUser(): UsersApi

    /**
     *  Gets tagged questions,
     *
     *  params:
     *  tags - semi-colon delimited list of tags
     *
     */
    @GET("search?site=stackoverflow&key=$API_KEY")
    suspend fun getTaggedQuestions(@Query("tagged") tags: String,
                                   @Query("page") page: Int = 1,
                                   @Query("pagesize") pageSize: Int = 20): SearchQuestionsApi

    @GET("tags?site=stackoverflow&sort=popular&order=desc")
    suspend fun getPopularTags(): TagsApi
}