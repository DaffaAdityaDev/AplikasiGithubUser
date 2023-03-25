package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.ItemsItem
import com.example.githubuser.UserResponse
import com.example.githubuser.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {

    private val TAG = "MainActivityViewModel"
    private val QueryUsername = "ad"
    private val userList = MutableLiveData<List<ItemsItem>>()

    fun getUserList(): MutableLiveData<List<ItemsItem>> {
        return userList
    }

    fun findUser(name: String = QueryUsername) {
        val client = ApiConfig.getApiService().getUser(name)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    val listUserBody = response.body()
                    if (listUserBody != null) {
                        userList.postValue(listUserBody.items)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}