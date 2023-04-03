package com.example.githubuser.repository

import android.app.Application
import com.example.githubuser.database.FavoriteRoomDatabase
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.database.FavoriteUserDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getAllNotes(): List<FavoriteUser> = mFavoriteUserDao.getAllUsers()

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute {
            mFavoriteUserDao.insert(favoriteUser)
        }
    }

    fun delete(favoriteUser: FavoriteUser) {
        executorService.execute {
            mFavoriteUserDao.delete(favoriteUser)
        }
    }

    fun update(favoriteUser: FavoriteUser) {
        executorService.execute {
            mFavoriteUserDao.update(favoriteUser)
        }
    }
}