package com.example.stackexchange.domain.models

import com.squareup.moshi.Json
import java.util.*

data class User(
        val reputation: Int = 0,
        val userId: Long,
        val userType: String = USER_TYPE,
        val profileImage: String = "",
        val displayName: String = "",
        val link: String = "",
        val creationDate: Date = Date()
){
        companion object{
                private const val USER_TYPE = "unregistered"
        }
}