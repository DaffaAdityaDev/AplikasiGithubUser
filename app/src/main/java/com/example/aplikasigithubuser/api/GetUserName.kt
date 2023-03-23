package com.example.aplikasigithubuser.api

import com.example.aplikasigithubuser.model.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface GetUserName {
    @GET("/search/users")
    @Headers("Authorization: token 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")
    suspend fun getUser(@Query("q") username: String): GithubUserResponse
}

interface GetDetailUser {
    @GET("/users/{username}")
    @Headers("Authorization: token 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")
    suspend fun getDetail(@Path("username") username: String): GithubUserResponse
}