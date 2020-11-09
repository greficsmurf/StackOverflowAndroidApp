package com.example.stackexchange.di

import com.example.stackexchange.MainActivity
import com.example.stackexchange.ui.authuser.AuthUserFragment
import com.example.stackexchange.ui.home.HomeFragment
import com.example.stackexchange.ui.home.HomeQuestionsTab
import com.example.stackexchange.ui.question.QuestionFragment
import com.example.stackexchange.ui.search.SearchFragment
import com.example.stackexchange.ui.search.SearchOptionsBottomDialog
import com.example.stackexchange.ui.tagsearch.TagSearchFragment
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
    abstract fun contributeAuthUserFragment(): AuthUserFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchOptionsBottomDialog(): SearchOptionsBottomDialog

    @ContributesAndroidInjector
    abstract fun contributeTagSearchFragment(): TagSearchFragment
}