package com.example.stackexchange.api.models

import com.squareup.moshi.Json

data class UsersApi(
    @Json(name = "items")
    val users: List<UserApi>
)