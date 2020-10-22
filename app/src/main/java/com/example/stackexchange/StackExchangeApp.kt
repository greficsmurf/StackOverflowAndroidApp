package com.example.stackexchange

import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import com.example.stackexchange.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasContentProviderInjector
import timber.log.Timber
import javax.inject.Inject

class StackExchangeApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}