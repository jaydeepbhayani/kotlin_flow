package com.jaydeepbhayani.league.view.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaydeepbhayani.league.data.model.LoginResponse
import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse
import com.jaydeepbhayani.league.domain.repository.RemoteRepository
import com.jaydeepbhayani.league.util.Resource
import com.jaydeepbhayani.league.util.UiState
import com.jaydeepbhayani.league.util.mapToPostItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private var repository: RemoteRepository
) : ViewModel() {

    private var mutableLogin = MutableStateFlow(LoginResponse(null))
    val login get() = mutableLogin.asStateFlow()

    private var mutableUsers = MutableStateFlow(emptyList<UsersResponse>())
    val users get() = mutableUsers.asStateFlow()

    private var mutablePosts = MutableStateFlow(emptyList<PostsResponse>())
    val posts get() = mutablePosts.asStateFlow()

    private val mutablePostItem =
        MutableStateFlow<UiState<List<PostItemModel>>>(UiState.Loading(true))
    val postItem get() = combine(mutableUsers, mutablePosts) { mutableUsers, mutablePosts ->
        mutablePosts.mapToPostItemModel(mutableUsers).let {
            when {
                it.isNotEmpty() -> UiState.Success(it)
                else -> UiState.Loading(true)
            }
            Log.i("asd","asd")
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Loading(true))

    init {
        getLoginData()
        combine(mutableUsers, mutablePosts) { mutableUsers, mutablePosts ->
            mutablePostItem.value = mutablePosts.mapToPostItemModel(mutableUsers).let {
                when {
                    it.isNotEmpty() -> UiState.Success(it)
                    else -> UiState.Loading(true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getLoginData() = viewModelScope.launch {
        repository.getLoginData()
            .collect { result ->
                when (result) {
                    is Resource.Success -> {
                        mutableLogin.value.api_key = result.data?.api_key
                        mutableLogin.value.api_key?.let {
                            getUsersData(it)
                            getPostsData(it)
                        }
                    }
                    else -> {}
                }
            }
    }

    private fun getUsersData(apiKey: String) {
        viewModelScope.launch {
            repository.getUsersData(apiKey)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> mutableUsers.value = result.data ?: emptyList()
                        else -> {}
                    }
                }
        }
    }

    private fun getPostsData(apiKey: String) {
        viewModelScope.launch {
            repository.getPostsData(apiKey)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> mutablePosts.value = result.data ?: emptyList()
                        else -> {}
                    }
                }
        }
    }
}