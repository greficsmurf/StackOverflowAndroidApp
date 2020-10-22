package com.example.stackexchange

import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.adapters.DateAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.lang.Exception

class StackOverflowApiUnitTest {

    lateinit var apiService: StackOverflowService

    @Before
    fun before(){
        @Suppress("SpellCheckingInspection")
        val moshi = Moshi.Builder()
                .add(DateAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

        apiService = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("https://api.stackexchange.com/2.2/")
                .build()
                .create(StackOverflowService::class.java)
    }

    @Test
    fun getQuestions() = runBlocking {
        try {
            val res = apiService.getQuestions("android")
            print(res)
            assert(res != null)
        }catch (e: Exception){
            print(e.message)
            assert(false)
        }
    }

    @Test
    fun getAllQuestions() = runBlocking {
        try {
            val res = apiService.getAllQuestions(10)
            print(res)
            assert(res != null)
        }catch (e: Exception){
            print(e.message)
            assert(false)
        }
    }
}