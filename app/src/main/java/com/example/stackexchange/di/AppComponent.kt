package com.example.stackexchange.di

import android.app.Application
import com.example.stackexchange.StackExchangeApp
import com.example.stackexchange.base.BaseApplication
import com.example.stackexchange.datasource.PagedSearchQuestionsDataSource
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            MainActivityModule::class,
            ViewModelModule::class
        ]
)
interface AppComponent{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: BaseApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)

    fun inject(cl: PagedSearchQuestionsDataSource)
}