package com.example.stackexchange.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.stackexchange.domain.models.SearchQuestion
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

    val questionsDataSource = _searchString.switchMap {
        repo.getSearchedQuestionsDataSource(it, 20).cachedIn(viewModelScope)
    }


    fun setSearchText(searchText: String){
        if(_searchString.value != searchText)
            _searchString.postValue(searchText)
    }
}