package com.example.aplikasigithubuser.api

import com.example.aplikasigithubuser.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: GetUserName by lazy {
        retrofit.create(GetUserName::class.java)
    }

    val detailApi: GetDetailUser by lazy {
        retrofit.create(GetDetailUser::class.java)
    }
}