package com.example.aplikasigithubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasigithubuser.model.GithubUserModel
import com.example.aplikasigithubuser.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val userListResponse: MutableLiveData<List<GithubUserModel>> = MutableLiveData()
    val DetailUserResponse: MutableLiveData<GithubUserModel> = MutableLiveData()

    fun getPost(query: String) {
        viewModelScope.launch {
            val response = repository.getUserItem(query, userListResponse)
            Log.d ("Response", response.toString() + "RAW MainView")
            if (response.isNotEmpty()) {
                userListResponse.value = response
            } else {
                Log.d("Response", response.toString() + "Failed MainView")
            }
        }
    }

    fun DetailUserResponse(username: String) {
        viewModelScope.launch {
            val response = repository.getDetailUser(username, DetailUserResponse)
            Log.d ("Response", response.toString() + "RAW MainView")
            if (response != null) {
                DetailUserResponse.value = response as GithubUserModel
            } else {
                Log.d("Response", response.toString() + "Failed MainView")
            }
        }
    }

}