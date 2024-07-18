package com.example.therastreator.data

import com.google.gson.annotations.SerializedName

class LoginJson(
    @SerializedName("status") val status: String? = null,
    @SerializedName("user") val user: String? = null,
    @SerializedName("pass") val pass: String? = null,
    @SerializedName("token") val token: String? = null
)