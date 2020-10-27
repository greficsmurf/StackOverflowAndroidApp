package com.example.stackexchange.domain.models

import com.squareup.moshi.Json
import java.util.*

data class User(
        val reputation: Int,
        val userId: Long,
        val userType: String,
        val profileImage: String,
        val displayName: String,
        val link: String,
        val creationDate: Date
){
    companion object{
        const val AUTH_USER_ID = -21414L
    }
}