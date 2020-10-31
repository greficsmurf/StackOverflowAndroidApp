package com.example.stackexchange.repo

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.stackexchange.api.StackOverflowService
import com.example.stackexchange.api.models.UserApi
import com.example.stackexchange.api.models.UsersApi
import com.example.stackexchange.base.BaseApplication
import com.example.stackexchange.datasource.PagedSearchQuestionsDataSource
import com.example.stackexchange.datasource.UserQuestionsDataSource
import com.example.stackexchange.db.dao.UserDao
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.interfaces.ResourceCallback
import com.example.stackexchange.vo.NetworkResource
import com.example.stackexchange.vo.Resource
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
        private val stackOverflowService: StackOverflowService,
        private val userDao: UserDao,
        private val application: BaseApplication
){
    fun getUserResource(id: Long) = object : NetworkResource<UsersApi, User>(){
        override suspend fun fetch(): UsersApi = stackOverflowService.getUser(id)

        override fun toDomainModel(data: UsersApi): User = data.users.map { it.toDomainModel() }.first()
    }

    fun getAuthUserResource() = object : NetworkResource<UsersApi, User>(){
        override suspend fun fetch(): UsersApi = stackOverflowService.getAuthUser()

        override fun toDomainModel(data: UsersApi): User = data.users.map { it.toDomainModel() }.first()

        override fun onError(e: Exception): Resource<User> {
            return if(e is HttpException)
                return Resource.authFailed(null, object : ResourceCallback{
                    override fun handle() {
                        application.stackOverflowAuthenticator.logIn()
                    }
                })
            else
                super.onError(e)
        }
    }.asLiveData()

    fun getUserQuestionsDataSource(
            userId: Long, pageSize: Int = 20
    ) : LiveData<PagingData<SearchQuestion>> = Pager(PagingConfig(initialLoadSize = 20, pageSize = pageSize)){
        UserQuestionsDataSource(
                stackOverflowService,
                userId
        )
    }.liveData
}