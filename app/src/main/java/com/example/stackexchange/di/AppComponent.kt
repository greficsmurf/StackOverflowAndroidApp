package com.example.stackexchange.di

import android.app.Application
import com.example.stackexchange.StackExchangeApp
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
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: StackExchangeApp)
}