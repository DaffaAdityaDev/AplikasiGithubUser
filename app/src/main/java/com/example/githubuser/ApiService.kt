package com.example.githubuser

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<UserResponse>
}