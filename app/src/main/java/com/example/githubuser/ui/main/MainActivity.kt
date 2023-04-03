package com.example.githubuser.ui.main

import android.app.SearchManager
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import androidx.appcompat.widget.SearchView
import com.example.githubuser.ItemsItem
import com.example.githubuser.R
import com.example.githubuser.UserResponse
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.database.FavoriteRoomDatabase
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.ui.favorite.FavoriteUserList
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var favoriteRoomDatabase: FavoriteRoomDatabase
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    // for variable declaration TAG and Query
    companion object {
        private const val TAG = "MainActivity"
        private const val QueryUsername = "a"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Home"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.teal_700)))

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        insertFavoriteUser(FavoriteUser("test", "urlTest"))

        findUser()
    }
    private fun insertFavoriteUser(favoriteUser: FavoriteUser) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Access the database here
                favoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(applicationContext)
                favoriteRoomDatabase.favoriteUserDao().insert(favoriteUser)
                val favoriteUsers = favoriteRoomDatabase.favoriteUserDao().getAllUsers()
                Log.d(TAG, "onCreate: $favoriteUsers")
            }
        }
    }

    private fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Access the database here
                favoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(applicationContext)
                favoriteRoomDatabase.favoriteUserDao().delete(favoriteUser)
                val favoriteUsers = favoriteRoomDatabase.favoriteUserDao().getAllUsers()
                Log.d(TAG, "onCreate: $favoriteUsers")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    // get data from api and call, setLoading Status and call setListUsersData if response is success
    // if response is not success, show error message
    fun findUser(name: String = QueryUsername) {
        var emptyData = emptyList<ItemsItem>()
        setListUsersData(emptyData)
        showLoading(true)
        val client = ApiConfig.getApiService().getUser(name)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val listUserBody = response.body()
                    if (listUserBody != null) {
                        showLoading(false)
                        setListUsersData(listUserBody.items)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                showLoading(false)
            }
        })
    }

    // set data to adapter and set adapter to recyclerview
    // this function is called in findUser function
    private fun setListUsersData(users: List<ItemsItem>) {
        val listUserData = ArrayList<ItemsItem>()
        for (user in users) {
            listUserData.add(user)
        }
        val userAdapter = UserAdapter(listUserData, this)
        binding.rvUsers.adapter = userAdapter
    }

    // set loading status
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    // implement searchview in option menu
    // when user submit query, call findUser function
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "search user..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                findUser(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                return true
            }
        })

        menuInflater.inflate(R.menu.fab_favorite, menu)
        val menuFavorite = menu?.findItem(R.id.favorite)
        menuFavorite?.setOnMenuItemClickListener {
            val intent = Intent(this, FavoriteUserList::class.java)
            startActivity(intent)
            true
        }

        return true
    }
}