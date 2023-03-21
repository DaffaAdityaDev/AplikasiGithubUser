package com.example.aplikasigithubuser.repository

import com.example.aplikasigithubuser.api.RetrofitInstance
import com.example.aplikasigithubuser.model.GithubUserModel
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<List<GithubUserModel>> {
        return RetrofitInstance.api.getUserList()
    }

    suspend fun getUserItem(): Response<List<GithubUserModel>> {
        return RetrofitInstance.api.getUserList()
    }
}