package com.example.stackexchange.domain.mappers

import com.example.stackexchange.api.models.UserApi
import com.example.stackexchange.domain.models.User

fun UserApi.toDomainModel() = User(
        reputation,
        userId,
        userType,
        profileImage,
        displayName,
        link
)