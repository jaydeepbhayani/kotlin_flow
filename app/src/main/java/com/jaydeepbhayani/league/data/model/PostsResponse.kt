package com.jaydeepbhayani.league.data.model

import com.google.gson.annotations.SerializedName

data class PostsResponse(
    @SerializedName("userId")
    var userId: Int? = 0,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("body")
    var body: String? = null,
)

data class PostItemModel(
    var title: String? = null,
    var body: String? = null,
    var image: String? = null,
    var name: String? = null,
)
