package com.example.githubuser.database

import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(FavoriteUser: FavoriteUser)

    @Update
    fun update(FavoriteUser: FavoriteUser)

    @Delete
    fun delete(FavoriteUser: FavoriteUser)

    @Query("DELETE FROM FavoriteUser")
    fun deleteAll()

    @Query("DELETE FROM FavoriteUser WHERE username = :username")
    fun deleteSelectedUser(username: String)

    @Query("SELECT * from FavoriteUser WHERE username = :username ORDER BY username ASC")
    fun getSelectedUser(username: String): FavoriteUser


    @Query("SELECT * from FavoriteUser ORDER BY username ASC")
    fun getAllUsers(): List<FavoriteUser>
}