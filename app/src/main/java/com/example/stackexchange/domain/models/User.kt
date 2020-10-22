package com.example.stackexchange.domain.models

import com.squareup.moshi.Json

data class User(
        val reputation: Int,
        val userId: Long,
        val userType: String,
        val profileImage: String,
        val displayName: String,
        val link: String
)