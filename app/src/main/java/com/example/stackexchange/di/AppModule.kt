package com.example.stackexchange.di

import android.app.Application
import androidx.room.Room
import com.example.stackexchange.StackExchangeApp
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.adapters.DateAdapter
import com.example.stackexchange.base.BaseApplication
import com.example.stackexchange.db.AppDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideDatabase(app: BaseApplication) = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "AppDatabase"
    ).build()


    @Singleton
    @Provides
    fun provideStackOverflowService(app: BaseApplication): StackOverflowService{
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(app.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor {  chain ->
                    val token = app.stackOverflowAuthenticator.getAuthToken()
                    if(token != null){
                        val request = chain.request()
                        val url = request.url().newBuilder().addEncodedQueryParameter("access_token", token).build()
                        chain.proceed(request.newBuilder().url(url).build())
                    }
                    else
                        chain.proceed(chain.request())
                }
                .build()

        @Suppress("SpellCheckingInspection")
        val moshi = Moshi.Builder()
                .add(DateAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("https://api.stackexchange.com/2.2/")
                .client(okHttpClient)
                .build()
                .create(StackOverflowService::class.java)
    }





    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase) = db.getUserDao()

    @Singleton
    @Provides
    fun provideSearchQuestionDao(db: AppDatabase) = db.getSearchQuestionDao()

    @Singleton
    @Provides
    fun provideHotQuestionDao(db: AppDatabase) = db.getHotQuestionDao()
    @Singleton
    @Provides
    fun provideWeekQuestionDao(db: AppDatabase) = db.getWeekQuestionDao()
    @Singleton
    @Provides
    fun provideMonthQuestionDao(db: AppDatabase) = db.getMonthQuestionDao()
    @Singleton
    @Provides
    fun provideTagDao(db: AppDatabase) = db.getTagDao()
}