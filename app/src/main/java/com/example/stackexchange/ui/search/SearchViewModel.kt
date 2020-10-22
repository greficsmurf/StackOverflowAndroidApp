package com.example.stackexchange.ui.search

import androidx.lifecycle.*
import com.example.stackexchange.domain.models.SearchQuestions
import com.example.stackexchange.repo.SearchRepo
import com.example.stackexchange.vo.Resource
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val repo: SearchRepo
): ViewModel(){

    private val _searchString = MutableLiveData<String>()
    val searchString: LiveData<String>
        get() = _searchString

    val questionsResource = _searchString.switchMap {
        if(it.isNotBlank())
            repo.searchQuestions(it).asLiveData()
        else
            liveData { emit(Resource.loaded(SearchQuestions(listOf()))) }
    }

    val questions = questionsResource.map {
        it.data
    }

    fun setSearchText(searchText: String){
        if(_searchString.value != searchText)
            _searchString.postValue(searchText)
    }
}