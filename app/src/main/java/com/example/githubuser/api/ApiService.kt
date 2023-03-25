package com.example.githubuser.api

import com.example.githubuser.UserResponse
import com.example.githubuser.modelResponse.UserDetailResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    companion object {
        const val AUTHTOKEN = "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"
    }
    @GET("search/users")
    @Headers("Authorization: token $AUTHTOKEN")
    fun getUser(
        @Query("q") username: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $AUTHTOKEN")
    fun getUseDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>
}