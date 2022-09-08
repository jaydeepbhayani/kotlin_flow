package com.jaydeepbhayani.league.domain.repository

import com.jaydeepbhayani.league.data.model.LoginResponse
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse
import com.jaydeepbhayani.league.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * * [RemoteRepository]
 * Handle remote data
 * Add the data here and handle in the implementation
 * @author
 * created by Jaydeep Bhayani on 09/08/2022
 */

interface RemoteRepository {
    suspend fun getLoginData(): Flow<Resource<LoginResponse>>

    suspend fun getUsersData(apiKey: String): Flow<Resource<List<UsersResponse>>>

    suspend fun getPostsData(apiKey: String): Flow<Resource<List<PostsResponse>>>
}