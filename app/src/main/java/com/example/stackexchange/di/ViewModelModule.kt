package com.example.stackexchange.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackexchange.ui.home.HomeViewModel
import com.example.stackexchange.ui.question.QuestionViewModel
import com.example.stackexchange.ui.search.SearchViewModel
import com.example.stackexchange.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
abstract class ViewModelModule{

    @Binds
    @IntoSet
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    @IntoSet
    abstract fun bindQuestionViewModel(vm: QuestionViewModel): ViewModel

    @Binds
    @IntoSet
    abstract fun bindSearchViewModel(vm: SearchViewModel): ViewModel

    @Binds
    @IntoSet
    abstract fun bindUserViewModel(vm: UserViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}