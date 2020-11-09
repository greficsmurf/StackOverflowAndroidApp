package com.example.stackexchange.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.stackexchange.R
import com.example.stackexchange.StackExchangeApp
import com.example.stackexchange.base.BaseApplication
import com.google.android.material.transition.MaterialFadeThrough
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector{
    lateinit var injector: AppComponent
    fun init(app: BaseApplication){
        injector = DaggerAppComponent
                .builder()
                .application(app)
                .build().apply {
                    inject(app)
                }


        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks{
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

            override fun onActivityResumed(activity: Activity) {
            }

        })
    }

    fun handleActivity(activity: Activity){
        if(activity is HasSupportFragmentInjector){
            AndroidInjection.inject(activity)
        }
        if(activity is FragmentActivity){
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks(){
                override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                    f.apply {
                        enterTransition = MaterialFadeThrough().apply {
                            duration = 500
                        }
                        exitTransition = MaterialFadeThrough().apply {
                            duration = 500
                        }
                    }
                    if(f is Injectable)
                        AndroidSupportInjection.inject(f)
                }
            }, true)
        }
    }
}