package com.example.stackexchange.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.stackexchange.repo.HomeRepo
import com.example.stackexchange.repo.QuestionSort
import com.example.stackexchange.utils.forceRefresh
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo
) : ViewModel(){
    private val _questionSort = MutableLiveData<QuestionSort>()

    val questionsDataSource = _questionSort.switchMap {
        homeRepo.getHomeQuestionsDataSource(20, it).cachedIn(viewModelScope)
    }

//    val questions = questionsResource.map {
//        it.data
//    }

    fun refresh(){
        _questionSort.forceRefresh()
    }

    fun setQuestionSort(sort: QuestionSort?){
        if(_questionSort.value != sort && sort != null)
            _questionSort.value = sort
    }
}