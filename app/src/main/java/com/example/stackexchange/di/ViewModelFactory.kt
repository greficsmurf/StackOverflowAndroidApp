package com.example.stackexchange.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
        private val creators: Set<@JvmSuppressWildcards ViewModel>
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = creators.firstOrNull{
            modelClass.isAssignableFrom(it::class.java)
        } ?: throw Exception("ViewModel is not recognized")

        return viewModel as T
    }
}