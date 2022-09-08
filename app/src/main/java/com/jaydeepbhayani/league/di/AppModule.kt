package com.jaydeepbhayani.league.di

import com.jaydeepbhayani.league.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * * [AppModule]
 * implementation for AppModule Injection
 * @author
 * created by Jaydeep Bhayani on 09/08/2022
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    /*@Provides
    @Singleton
    fun provideRemoteRepository(apiService: ApiService): RemoteRepository {
        return RemoteRepositoryImpl(apiService)
    }*/
}