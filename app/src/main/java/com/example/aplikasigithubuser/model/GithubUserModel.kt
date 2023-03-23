package com.example.aplikasigithubuser.model

import com.google.gson.annotations.SerializedName

data class GithubUserModel(
    val login: String? = null,
    val id: Long? = null,
    val avatar_url: String? = null,
    val followers_url: String? = null,
    val following_url: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val public_repos: Int? = null,
    val followers: Int? = null,
    val following: Int? = null
)

data class GithubUserResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val items: List<GithubUserModel>
)