package com.example.stackexchange.ui.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackexchange.vo.Resource
import javax.inject.Inject

class QuestionViewModel @Inject constructor() : ViewModel(){

    private val _pageResource = MutableLiveData<Resource<Boolean>>()
    val pageResource: LiveData<Resource<Boolean>>
        get() = _pageResource

    fun setPageLoaded(isLoaded: Boolean){
        if(isLoaded)
            _pageResource.value = Resource.loaded(isLoaded)
        else
            _pageResource.value = Resource.loading(isLoaded)
    }

    fun setError(msg: String = ""){
        _pageResource.value = Resource.failed(null, msg)
    }

}