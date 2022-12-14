package com.jaydeepbhayani.league.util

import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse

public fun List<PostsResponse>.mapToPostItemModel(userResponseList: List<UsersResponse?>): List<PostItemModel> {
    return if (userResponseList.isNotEmpty() && this.isNotEmpty()) {
        map { post ->
            val user = userResponseList.find { it?.id == post.userId }
            PostItemModel(
                title = post.title,
                body = post.body,
                image = user?.avatar,
                name = user?.name
            )
        }
    } else emptyList()
}