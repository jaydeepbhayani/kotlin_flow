package com.jaydeepbhayani.league.data.repository

import com.jaydeepbhayani.league.data.model.LoginResponse
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse
import com.jaydeepbhayani.league.data.remote.ApiService
import com.jaydeepbhayani.league.domain.repository.RemoteRepository
import com.jaydeepbhayani.league.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * * [RemoteRepositoryImpl]
 * implementation for RemoteRepositoryImpl
 * @author
 * created by Jaydeep Bhayani on 09/08/2022
 */

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    /*private val dispatcher: DispatcherProvider*/
) : RemoteRepository {

    override suspend fun getLoginData(): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val result = apiService.getLoginDataAsync()
                emit(Resource.Success(result.body()))
                emit(Resource.Loading(false))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: Throwable) {
                emit(Resource.Error("Couldn't load data"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUsersData(apiKey: String): Flow<Resource<List<UsersResponse>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val result = apiService.getUsersDataAsync(apiKey)
                emit(Resource.Success(result))
                emit(Resource.Loading(false))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getPostsData(apiKey: String): Flow<Resource<List<PostsResponse>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val result = apiService.getPostsData(apiKey)
                emit(Resource.Success(result))
                emit(Resource.Loading(false))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: Throwable) {
                emit(Resource.Error(e.message))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object
}