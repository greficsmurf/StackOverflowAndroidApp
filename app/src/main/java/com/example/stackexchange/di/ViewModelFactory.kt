package com.example.stackexchange.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(
        private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = creators[modelClass] ?: creators.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key::class.java)
        }?.value ?: throw Exception("ViewModel is not recognized")

        return viewModel.get() as T
    }
}