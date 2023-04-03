package com.example.githubuser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.ItemsItem
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.database.FavoriteRoomDatabase
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.databinding.ActivityFavoriteUserListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteUserList : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserListBinding

    private lateinit var favoriteRoomDatabase: FavoriteRoomDatabase
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var favoriteUsers: List<FavoriteUser> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUser.layoutManager = layoutManager

        getFavoriteUser()

    }

    private fun getFavoriteUser() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Access the database here
                favoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(applicationContext)
                favoriteUsers = favoriteRoomDatabase.favoriteUserDao().getAllUsers()
            }
            setListUsersData(favoriteUsers)
        }
    }

    private fun setListUsersData(users: List<FavoriteUser>) {
        val listUserData = ArrayList<ItemsItem>()
        Log.d("FavoriteUserList", "setListUsersData: $users")
        for (user in users) {
            listUserData.add(ItemsItem(login = user.username, htmlUrl = user.avatarUrl))
        }
        val userAdapter = UserAdapter(listUserData, this)
        binding.rvFavoriteUser.adapter = userAdapter
    }
}