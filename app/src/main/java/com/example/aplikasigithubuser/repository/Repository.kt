package com.example.aplikasigithubuser.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.aplikasigithubuser.api.RetrofitInstance
import com.example.aplikasigithubuser.model.GithubUserModel
import com.example.aplikasigithubuser.model.GithubUserResponse

class Repository {

    private var currentQuery: String? = null

    suspend fun getUserItem(query: String, userListResponse: MutableLiveData<List<GithubUserModel>>): List<GithubUserModel> {
        if (currentQuery == query) {
            // If the query is the same as the previous query, return the cached response
            return userListResponse.value ?: emptyList()
        }
        return try {
            Log.d("Response", "RAW Repository")
            val response = RetrofitInstance.api.getUser(query)
            currentQuery = query // Update the current query
            response.items // return the list of GithubUserModel objects from the GithubUserResponse object
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getDetailUser(username: String, DetailUserResponse: MutableLiveData<GithubUserModel>): GithubUserResponse? {
        return try {
            Log.d("Response", "RAW Repository")
            val response = RetrofitInstance.detailApi.getDetail(username)
            DetailUserResponse.value = response.items as GithubUserModel
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
