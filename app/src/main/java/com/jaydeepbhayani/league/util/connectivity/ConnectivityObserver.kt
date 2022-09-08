package com.jaydeepbhayani.league.util.connectivity

import kotlinx.coroutines.flow.Flow

/**
 * * [ConnectivityObserver]
 * Connectivity Interface Observer
 * @author
 * created by Jaydeep Bhayani on 09/08/2022
 */
interface ConnectivityObserver {

    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}