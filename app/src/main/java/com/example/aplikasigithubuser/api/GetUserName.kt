package com.example.aplikasigithubuser.api

import com.example.aplikasigithubuser.model.GithubFollowersResponse
import com.example.aplikasigithubuser.model.GithubUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface GetUserName {
    @GET("/users/{username}/followers")
    @Headers("Authorization: token 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")
    suspend fun getFollowers(@Path("username") username: String): List<GithubUserModel>
}
