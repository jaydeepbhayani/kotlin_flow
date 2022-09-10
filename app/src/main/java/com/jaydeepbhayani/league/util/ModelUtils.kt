package com.jaydeepbhayani.league.util

import com.jaydeepbhayani.league.data.model.PostItemModel
import com.jaydeepbhayani.league.data.model.PostsResponse
import com.jaydeepbhayani.league.data.model.UsersResponse

fun List<PostsResponse>.mapToPostItemModel(userResponseList: List<UsersResponse?>): List<PostItemModel> {
    if (userResponseList.isNotEmpty() && this.isNotEmpty()) {
        return map { post ->
            val user = userResponseList.find { it?.id == post.userId }
            PostItemModel(
                title = post.title,
                body = post.body,
                image = user?.avatar,
                name = user?.name
            )
        }
    } else return emptyList()
}