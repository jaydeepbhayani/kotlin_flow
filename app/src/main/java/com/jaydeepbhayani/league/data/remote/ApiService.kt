package com.jaydeepbhayani.league.data.remote

import com.jaydeepbhayani.league.data.model.LoginResponse
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * * [ApiService]
 * Api service interface to handle all the data from retrofit
 * @author
 * created by Jaydeep Bhayani on 09/09/2022
 */
interface ApiService {

    @GET("login")
    suspend fun getLoginDataAsync(): Response<LoginResponse>

    @GET("users")
    suspend fun getUsersDataAsync(@Header("x-access-token") apiKey: String): List<UsersResponse>

    @GET("posts")
    suspend fun getPostsData(@Header("x-access-token") apiKey: String): List<PostsResponse>

    companion object {
        const val BASE_URL = "https://engineering.league.dev/challenge/api/"
    }
}