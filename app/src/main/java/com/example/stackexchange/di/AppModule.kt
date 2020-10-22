package com.example.stackexchange.di

import android.app.Application
import androidx.room.Room
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.adapters.DateAdapter
import com.example.stackexchange.db.AppDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class AppModule{

    @Singleton
    @Provides
    fun provideDatabase(app: Application) = Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "AppDatabase"
    ).build()


    @Singleton
    @Provides
    fun provideStackOverflowService(): StackOverflowService{
        @Suppress("SpellCheckingInspection")
        val moshi = Moshi.Builder()
                .add(DateAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("https://api.stackexchange.com/2.2/")
                .build()
                .create(StackOverflowService::class.java)
    }

}