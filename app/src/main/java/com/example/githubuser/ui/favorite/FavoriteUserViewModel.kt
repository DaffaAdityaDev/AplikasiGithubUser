package com.example.githubuser.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.database.FavoriteRoomDatabase
import com.example.githubuser.database.FavoriteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteUserViewModel : ViewModel() {
    val favoriteUsersLiveData: MutableLiveData<List<FavoriteUser>> by lazy {
        MutableLiveData<List<FavoriteUser>>()
    }

    // rest of the code

}