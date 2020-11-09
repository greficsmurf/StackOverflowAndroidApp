package com.example.stackexchange.ui.tagsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.stackexchange.repo.SearchRepo
import javax.inject.Inject

class TagSearchViewModel @Inject constructor(
        private val searchRepo: SearchRepo
) : ViewModel(){

    private val _tags = MutableLiveData<List<String>>()

    val tagSearchQuestions = _tags.switchMap { list ->
        searchRepo.getTagSearchQuestionsDataSource(list, 20)
    }

    fun setTags(list: List<String>?){
        if(!list.isNullOrEmpty()){
            _tags.value = list
        }
    }
}