package com.example.githubuser

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.adapter.SectionPagerUserDetailAdapter
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.fragment.FollowFragment
import com.example.githubuser.modelResponse.UserDetailResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

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

    private fun getIntentData() : String? {
        val username = intent.getStringExtra("username")
        return username
    }

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

    private fun getDetailUser(username: String?) {
        val client = ApiConfig.getApiService().getUseDetail(username.toString())
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    val listUserBody = response.body()
                    if (listUserBody != null) {
                        setBindingData(listUserBody)
                    } else {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }

                    if (response.message() == "Not Found") {
                        Log.e(TAG, "onResponse: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

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

}