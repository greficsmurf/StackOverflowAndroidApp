package com.example.stackexchange.base

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.stackexchange.R
import com.example.stackexchange.authenticator.StackOverflowAuthenticator
import com.example.stackexchange.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

abstract class BaseApplication : Application(), HasActivityInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var stackOverflowAuthenticator: StackOverflowAuthenticator

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector


}