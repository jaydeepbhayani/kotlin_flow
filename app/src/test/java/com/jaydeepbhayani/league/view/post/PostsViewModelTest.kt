package com.jaydeepbhayani.league.view.post

import com.google.common.truth.Truth.assertThat
import com.jaydeepbhayani.league.MainDispatcherRule
import com.jaydeepbhayani.league.data.model.LoginResponse
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse
import com.jaydeepbhayani.league.repository.RemoteRepositoryFake
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostsViewModelTest {

    @get:Rule
    val coroutineRule = MainDispatcherRule()

    private lateinit var repositoryFake: RemoteRepositoryFake
    private lateinit var postsViewModel: PostsViewModel

    @Before
    fun setUp() {
        repositoryFake = RemoteRepositoryFake()
        postsViewModel = PostsViewModel(
            repository = repositoryFake
        )
    }

    @Test
    fun `login are properly mapped to state`() = runBlocking {
        var loginResponse = LoginResponse(
            api_key = "steadfast"
        )
        repositoryFake.loginResponse = loginResponse
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertThat(postsViewModel.login.value).isEqualTo(loginResponse)
    }

    @Test
    fun `user response are properly mapped to state`() = runBlocking {
        val userResponse = (1..5).map {
            UsersResponse(
                id = 0,
                avatar = "avatar",
                name = "name",
                username = "user",
            )
        }
        repositoryFake.userResponse = userResponse
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertThat(postsViewModel.users.value).isEqualTo(userResponse)
    }

    @Test
    fun `post response are properly mapped to state`() = runBlocking {
        val postResponse = (1..5).map {
            PostsResponse(
                userId = 0,
                id = 0,
                title = "Test",
                body = "Desc",
            )
        }
        repositoryFake.postResponse = postResponse
        coroutineRule.dispatcher.scheduler.advanceUntilIdle()
        assertThat(postsViewModel.posts.value).isEqualTo(postResponse)
    }
}