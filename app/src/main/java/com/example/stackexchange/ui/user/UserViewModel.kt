package com.example.stackexchange.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.repo.UserRepo
import timber.log.Timber
import javax.inject.Inject

class UserViewModel @Inject constructor(
        private val userRepo: UserRepo
) : ViewModel(){

    private val _userId = MutableLiveData<Long>()
    private var _token = ""
    val reputation = MutableLiveData<String>().apply { value = "" }


    val userInfoResource = _userId.switchMap {
        if(it == User.AUTH_USER_ID)
            userRepo.getAuthUserResource(_token).asLiveData()
        else
            userRepo.getUserResource(it).asLiveData()
    }
    val userInfo = userInfoResource.map {
        it.data
    }


    fun setUserId(id: Long){
        _userId.value = id
    }
    fun setToken(token: String){
        _token = token
    }
}