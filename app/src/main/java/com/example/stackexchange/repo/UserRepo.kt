package com.example.stackexchange.repo

import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.models.UserApi
import com.example.stackexchange.api.models.UsersApi
import com.example.stackexchange.db.dao.UserDao
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.vo.NetworkResource
import com.example.stackexchange.vo.Resource
import javax.inject.Inject

class UserRepo @Inject constructor(
        private val stackOverflowService: StackOverflowService,
        private val userDao: UserDao
){
    fun getUserResource(id: Long) = object : NetworkResource<UsersApi, User>(){
        override suspend fun fetch(): UsersApi = stackOverflowService.getUser(id)

        override fun toDomainModel(data: UsersApi): User = data.users.map { it.toDomainModel() }.first()
    }

    fun getAuthUserResource(token: String) = object : NetworkResource<UsersApi, User>(){
        override suspend fun fetch(): UsersApi = stackOverflowService.getAuthUser(token)

        override fun toDomainModel(data: UsersApi): User = data.users.map { it.toDomainModel() }.first()
    }
}