package com.jaydeepbhayani.league.repository

import com.jaydeepbhayani.league.MainDispatcherRule
import com.jaydeepbhayani.league.data.model.LoginResponse
import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse
import com.jaydeepbhayani.league.domain.repository.RemoteRepository
import com.jaydeepbhayani.league.util.Resource
import com.jaydeepbhayani.league.util.mapToPostItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.junit.Rule

class RemoteRepositoryFake : RemoteRepository {
    @get:Rule
    val coroutineRule = MainDispatcherRule()
    var loginResponse = LoginResponse(
        api_key = "api_key"
    )

    var userResponse = (1..10).map {
        UsersResponse(
            id = 0 + it,
            avatar = "avatar$it",
            name = "name$it",
            username = "username$it",
        )
    }
    var postResponse = (1..10).map {
        PostsResponse(
            userId = 0 + it,
            id = 0 + it,
            title = "title$it",
            body = "body$it",
        )
    }

    var postItemList = (1..10).map {
        PostItemModel(
            title = "title$it",
            body = "body$it",
            image = "avatar$it",
            name = "name$it",
        )
    }

    override suspend fun getLoginData(): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Success(loginResponse))
        }.flowOn(coroutineRule.dispatcher.scheduler)
    }

    override suspend fun getUsersData(apiKey: String): Flow<Resource<List<UsersResponse>>> {
        return flow {
            emit(Resource.Success(userResponse))
        }.flowOn(coroutineRule.dispatcher.scheduler)
    }

    override suspend fun getPostsData(apiKey: String): Flow<Resource<List<PostsResponse>>> {
        return flow {
            emit(Resource.Success(postResponse))
        }.flowOn(coroutineRule.dispatcher.scheduler)
    }

    suspend fun getPostItemData(): Flow<Resource<List<PostItemModel>>> {
        return flow {
            emit(Resource.Success(postResponse.mapToPostItemModel(userResponse)))
        }.flowOn(coroutineRule.dispatcher.scheduler)
    }
}