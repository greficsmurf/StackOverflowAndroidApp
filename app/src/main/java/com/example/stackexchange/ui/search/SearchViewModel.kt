package com.example.stackexchange.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.SearchQuestions
import com.example.stackexchange.domain.models.Tag
import com.example.stackexchange.repo.QuestionSort
import com.example.stackexchange.repo.SearchRepo
import com.example.stackexchange.utils.forceRefresh
import com.example.stackexchange.vo.Resource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class SearchViewModel @Inject constructor(
        private val repo: SearchRepo
): ViewModel(){
    private val _searchString = MutableLiveData<String>()
    val searchString: LiveData<String>
        get() = _searchString

    val questionsDataSource = _searchString.switchMap {
        repo.getSearchedQuestionsDataSource(it, 20, tags = selectedTagList.map { tag-> tag.name }).cachedIn(viewModelScope)
    }

    private val tagsResource = repo.getPopularTags().asLiveData()

    val tags = tagsResource.map {
        it.data
    }

    val selectedTagList = mutableListOf<Tag>()

    fun setSearchText(searchText: String){
        if(_searchString.value != searchText)
            _searchString.postValue(searchText)
    }

    fun refresh(){
        _searchString.forceRefresh()
    }

}