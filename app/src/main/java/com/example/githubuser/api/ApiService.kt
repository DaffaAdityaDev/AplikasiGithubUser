package com.example.githubuser.api

import com.example.githubuser.ItemsItem
import com.example.githubuser.UserResponse
import com.example.githubuser.modelResponse.DetailFollowResponse
import com.example.githubuser.modelResponse.FollowResponseItem
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

    @GET("users/{username}/{follow}")
    @Headers("Authorization: token $AUTHTOKEN")
    fun getFollow(
        @Path("username") username: String?,
        @Path("follow") follow: String
    ): Call<List<ItemsItem>>
}