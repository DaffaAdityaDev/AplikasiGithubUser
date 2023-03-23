package com.example.aplikasigithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.adapter.UserDetailPagerAdapter
import com.example.aplikasigithubuser.databinding.ActivityDetailUserBinding
import com.example.aplikasigithubuser.model.UserDetailModel
import com.example.aplikasigithubuser.repository.Repository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: MainViewModel

    private lateinit var data_followers_url: String
    private lateinit var data_following_url: String
    companion object {
        private val TAB_TITLES = arrayOf(
            "Follower",
            "Following"
        )

        private const val QUERY_KEY = "query_key"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var DataItem = intent.getSerializableExtra("DataUserItem") as UserDetailModel
        Log.d("DataUserItem", DataItem.toString())

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]




        // Set up the ViewPager and TabLayout
        val userDetailPagerAdapter = UserDetailPagerAdapter(this, DataItem)
        binding.viewPager.adapter = userDetailPagerAdapter

        val tabs = binding.tabs
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        // Load the user's avatar image using Glide
        Glide.with(this).load(DataItem.avatar_url).into(binding.ivDetailUser)

        supportActionBar?.elevation = 0f
    }
}