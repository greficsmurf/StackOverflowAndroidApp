package com.example.stackexchange.di

import com.example.stackexchange.MainActivity
import com.example.stackexchange.ui.home.HomeFragment
import com.example.stackexchange.ui.home.HomeQuestionsTab
import com.example.stackexchange.ui.question.QuestionFragment
import com.example.stackexchange.ui.search.SearchFragment
import com.example.stackexchange.ui.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeQuestionFragment(): QuestionFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributeQuestionTabFragment(): HomeQuestionsTab

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment
}