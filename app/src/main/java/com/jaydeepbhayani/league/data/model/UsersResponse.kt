package com.jaydeepbhayani.league.data.model

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("avatar")
    var avatar: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("username")
    var username: String? = null,
)
