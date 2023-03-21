package com.example.aplikasigithubuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aplikasigithubuser.model.GithubUserModel
import com.example.aplikasigithubuser.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val userListResponse: MutableLiveData<Response<List<GithubUserModel>>> = MutableLiveData()

    fun getPost() {
        viewModelScope.launch {
            val response = repository.getUserItem()
            if (response.isSuccessful) {
                userListResponse.value = response
            }

        }
    }

}