package com.example.stackexchange.domain.mappers

import com.example.stackexchange.api.models.UserApi
import com.example.stackexchange.db.models.UserDb
import com.example.stackexchange.domain.models.User
import java.util.*

fun UserApi.toDomainModel() = User(
        reputation,
        userId,
        userType,
        profileImage,
        displayName,
        link,
        creationDate
)

fun UserDb.toDomainModel() = User(
        reputation,
        userId,
        userType,
        profileImage,
        displayName,
        link,
        Date().apply { time = 0 }
)

fun User.toDbModel() = UserDb(
        userId,
        reputation,
        userType,
        profileImage,
        displayName,
        link
)