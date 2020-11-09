package com.example.stackexchange.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stackexchange.ui.authuser.AuthUserViewModel
import com.example.stackexchange.ui.home.HomeViewModel
import com.example.stackexchange.ui.question.QuestionViewModel
import com.example.stackexchange.ui.search.SearchViewModel
import com.example.stackexchange.ui.tagsearch.TagSearchViewModel
import com.example.stackexchange.ui.user.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel::class)
    abstract fun bindQuestionViewModel(vm: QuestionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(vm: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(vm: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthUserViewModel::class)
    abstract fun bindAuthUserViewModel(vm: AuthUserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TagSearchViewModel::class)
    abstract fun bindTagSearchViewModel(vm: TagSearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}