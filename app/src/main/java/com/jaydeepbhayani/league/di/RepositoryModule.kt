package com.jaydeepbhayani.league.di

import com.jaydeepbhayani.league.data.repository.RemoteRepositoryImpl
import com.jaydeepbhayani.league.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * * [RepositoryModule]
 * implementation for Bind Repository Injection
 * @author
 * created by Jaydeep Bhayani on 09/08/2022
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRemoteRepository(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): RemoteRepository

    /*@Binds
    @Singleton
    abstract fun bindDispatcher(
        remoteRepositoryImpl: RemoteRepositoryImpl
    ): DispatcherProvider*/
}