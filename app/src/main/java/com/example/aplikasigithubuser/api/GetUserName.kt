package com.example.aplikasigithubuser.api

import com.example.aplikasigithubuser.model.GithubUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface GetUserName {
    @GET("posts")
    @Headers("Authorization: token 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")
    suspend fun getUserList() : Response<List<GithubUserModel>>
}
