package com.example.stackexchange.ui.authuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.stackexchange.repo.UserRepo
import javax.inject.Inject

class AuthUserViewModel @Inject constructor(
        private val repo: UserRepo
) : ViewModel(){

    val userInfoResource = repo.getAuthUserResource()

    val userInfo = userInfoResource.map {
        it.data
    }
}