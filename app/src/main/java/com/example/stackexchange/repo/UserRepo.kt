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
import com.example.stackexchange.db.models.UserDb
import com.example.stackexchange.domain.mappers.toDbModel
import com.example.stackexchange.domain.mappers.toDomainModel
import com.example.stackexchange.domain.models.SearchQuestion
import com.example.stackexchange.domain.models.User
import com.example.stackexchange.interfaces.ResourceCallback
import com.example.stackexchange.utils.timeElapsed
import com.example.stackexchange.vo.NetworkDatabaseResource
import com.example.stackexchange.vo.NetworkResource
import com.example.stackexchange.vo.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepo @Inject constructor(
        private val stackOverflowService: StackOverflowService,
        private val userDao: UserDao,
        private val application: BaseApplication
){
    private val REFRESH_TIME = 60 * 60 * 1000L
    fun getUserResource(id: Long) = object : NetworkDatabaseResource<UsersApi, UserDb, User>(){
        override suspend fun fetchApi(): UsersApi = stackOverflowService.getUser(id)

        override fun toDomainModelApi(data: UsersApi): User = data.users.map { it.toDomainModel() }.first()
        override suspend fun fetchDb(): Flow<UserDb> = userDao.getById(id)
        override fun toDomainModelDb(data: UserDb): User = data.toDomainModel()

        override fun shouldFetchApi(data: UserDb?): Boolean = data?.let {
            it.updatedAt.timeElapsed() > REFRESH_TIME
        } ?: true

        override suspend fun onDbSave(data: User) = userDao.insertWithTimestamp(data.toDbModel())
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