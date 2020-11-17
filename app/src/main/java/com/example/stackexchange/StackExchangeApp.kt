package com.example.stackexchange

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.stackexchange.base.BaseApplication
import com.example.stackexchange.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasContentProviderInjector
import timber.log.Timber
import javax.inject.Inject

class StackExchangeApp : BaseApplication() {



    override fun onCreate() {
        super.onCreate()
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        initThemePreference(preferences)
    }

    private fun initThemePreference(pref: SharedPreferences){
        when(pref.getString(getString(R.string.theme_preference_key), "")){
            getString(R.string.light_theme_entry_value) -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO)
            getString(R.string.dark_theme_entry_value) -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES)
            getString(R.string.system_default_theme_entry_value) -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}