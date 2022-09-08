package com.jaydeepbhayani.league.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("api_key")
    var api_key: String? = null,
)
