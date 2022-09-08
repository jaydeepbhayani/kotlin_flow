package com.jaydeepbhayani.league.view

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
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

    private val mutablePostItem = MutableStateFlow<UiState<List<PostItemModel>>>(UiState.Loading)
    val postItem get() = mutablePostItem.asStateFlow()

    init {
        getLoginData()
        mutableUsers.combine(mutablePosts) { mutableUsers, mutablePosts ->
            mutablePostItem.value = mutablePosts.mapToPostItemModel(mutableUsers).let {
                if (it.isNotEmpty()) UiState.Success(emptyList()) else UiState.Loading
            }
        }.launchIn(viewModelScope)
    }

    private fun getLoginData() = viewModelScope.launch {
        repository.getLoginData()
            .collect { result ->
                when (result) {
                    is Resource.Loading -> {}
                    is Resource.Success ->
                        result.data?.api_key?.let {
                            getUsersData(it)
                            getPostsData(it)
                        }
                    is Resource.Error -> Unit
                }
            }
    }

    private fun getUsersData(apiKey: String) {
        viewModelScope.launch {
            repository.getUsersData(apiKey)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> mutableUsers.value = result.data ?: emptyList()
                        is Resource.Error -> Unit
                    }
                }
        }
    }

    private fun getPostsData(apiKey: String) {
        viewModelScope.launch {
            repository.getPostsData(apiKey)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {}
                        is Resource.Success -> mutablePosts.value = result.data ?: emptyList()
                        is Resource.Error -> Unit
                    }
                }
        }
    }
}