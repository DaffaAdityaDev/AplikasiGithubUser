package com.example.aplikasigithubuser.model

data class GithubUserModel(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val followers_url: String
)

data class GithubFollowersResponse(
    val items: List<GithubUserModel>
)