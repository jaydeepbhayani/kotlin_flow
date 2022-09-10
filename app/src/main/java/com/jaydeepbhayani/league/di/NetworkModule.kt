package com.jaydeepbhayani.league.di

import android.content.Context
import com.jaydeepbhayani.league.BuildConfig
import com.jaydeepbhayani.league.util.connectivity.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
 * * [NetworkModule]
 * implementation for Network Injection
 * @author
 * created by Jaydeep Bhayani on 09/09/2022
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideIntercepterOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
        }.build()
    }

    @Provides
    @Singleton
    fun provideNetworkStateObserver(@ApplicationContext context: Context) =
        NetworkConnectivityObserver(context)
}