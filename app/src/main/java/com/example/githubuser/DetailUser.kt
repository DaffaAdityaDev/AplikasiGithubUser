package com.example.githubuser

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.adapter.SectionPagerUserDetailAdapter
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.database.FavoriteRoomDatabase
import com.example.githubuser.database.FavoriteUser
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.modelResponse.UserDetailResponse
import com.example.githubuser.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUser : AppCompatActivity() {


    // using view binding
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var favoriteRoomDatabase: FavoriteRoomDatabase
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var user : FavoriteUser = FavoriteUser("","")

    // data for tab layout and log TAG
    companion object {
        private const val TAG = "DetailUser"
        private val TAB_TITLES = arrayListOf("Followers", "Following")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.teal_700)))

        val dataIntent = getIntentData()

        getDetailUser(dataIntent)

        setPager(getIntentData())

    }

    private fun checkFavoriteUser() {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Access the database here
                favoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(applicationContext)
                user = favoriteRoomDatabase.favoriteUserDao().getSelectedUser(getIntentData() as String)
                if (user == null) {
                    insertFavoriteUser(FavoriteUser(getIntentData().toString(), ""))
                } else {
                    deleteFavoriteUser(FavoriteUser(getIntentData().toString(), ""))
                }
                Log.d("bro", "onCreate:" + getIntentData())
            }
        }
    }

    private fun insertFavoriteUser(favoriteUser: FavoriteUser) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Access the database here
                favoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(applicationContext)
                favoriteRoomDatabase.favoriteUserDao().insert(favoriteUser)
            }
            invalidateOptionsMenu()
        }
    }

    private fun deleteFavoriteUser(favoriteUser: FavoriteUser) {
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                // Access the database here
                favoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(applicationContext)
                favoriteRoomDatabase.favoriteUserDao().delete(favoriteUser)
            }
            invalidateOptionsMenu()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fab_favorite, menu)

        val menuFavorite = menu?.findItem(R.id.favorite)
//        if (user.username == getIntentData().toString()) {
//            menu?.findItem(R.id.favorite)?.setIcon(R.drawable.favorite_border)
//        } else {
//            menu?.findItem(R.id.favorite)?.setIcon(R.drawable.favorite_fill)
//        }
        menuFavorite?.setOnMenuItemClickListener {
            checkFavoriteUser()
            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    // getting data from intent
    private fun getIntentData() : String? {
        val username = intent.getStringExtra("username")
        return username
    }

    // binding data to view
    private fun setBindingData(username: UserDetailResponse) {
        binding.tvNameDetailUser.text = username.name
        binding.tvGithubNameDetailUser.text = username.login
        Glide.with(this)
            .load(username.avatarUrl)
            .override(100, 100)
            .error(R.drawable.ic_launcher_foreground)
            .into(binding.ivImageDetailUser)
        binding.tvTotalFollowers.text = resources.getString(R.string.total_followers, username.followers)
        binding.tvTotalFollowing.text = resources.getString(R.string.total_following, username.following)

        supportActionBar?.title = "Detail User " + if (username.name == "null") username.login else username.name
    }

    // getDetailUser from api and call, setLoading Status and call setBindingData if response is success
    // if response is not success, show error message
    // if response message is "Not Found", show error message
    private fun getDetailUser(username: String?) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUseDetail(username.toString())
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val listUserBody = response.body()
                    if (listUserBody != null) {
                        showLoading(false)
                        setBindingData(listUserBody)
                    } else {
                        showLoading(false)
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }

                    if (response.message() == "Not Found") {
                        showLoading(false)
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // set pager adapter and tab layout
    private fun setPager(username: String?) {
        val sectionsPagerAdapter = SectionPagerUserDetailAdapter(this, username)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        supportActionBar?.elevation = 0f
    }

    // show loading
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}