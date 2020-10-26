package com.example.stackexchange.api.models

import com.squareup.moshi.Json


data class UserApi(
        val reputation: Int = 0,
        @Json(name = "user_id")
        val userId: Long = -1,
        @Json(name = "user_type")
        val userType: String = "does_not_exist",
        @Json(name = "profile_image")
        val profileImage: String = "",
        @Json(name = "display_name")
        val displayName: String = "",
        val link: String = ""
)