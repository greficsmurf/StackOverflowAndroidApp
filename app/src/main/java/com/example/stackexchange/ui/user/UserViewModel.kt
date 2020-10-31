package com.example.stackexchange.ui.user

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.repo.UserRepo
import timber.log.Timber
import javax.inject.Inject

class UserViewModel @Inject constructor(
        private val userRepo: UserRepo
) : ViewModel(){

    private val _userId = MutableLiveData<Long>()
    val reputation = MutableLiveData<String>().apply { value = "" }


    val userInfoResource = _userId.switchMap {
        userRepo.getUserResource(it).asLiveData()
    }

    val userInfo = userInfoResource.map {
        it.data
    }

    // to be changed, temp solution
    val userQuestionsDataSource = _userId.switchMap {
        userRepo.getUserQuestionsDataSource(it, 20).cachedIn(viewModelScope)
    }

    fun setUserId(id: Long){
        _userId.value = id
    }

}