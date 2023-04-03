package com.example.githubuser.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.repository.FavoriteUserRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllNotes() = mFavoriteUserRepository.getAllNotes()
}