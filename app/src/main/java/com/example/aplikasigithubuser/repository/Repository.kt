package com.example.aplikasigithubuser.repository

import com.example.aplikasigithubuser.api.RetrofitInstance
import com.example.aplikasigithubuser.model.GithubFollowersResponse
import com.example.aplikasigithubuser.model.GithubUserModel
import retrofit2.Response

class Repository {

    suspend fun getUserItem(): List<GithubUserModel> {
        return RetrofitInstance.api.getFollowers("sidiqpermana")
    }
}