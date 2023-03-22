package com.example.aplikasigithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.adapter.UserDetailPagerAdapter
import com.example.aplikasigithubuser.databinding.ActivityDetailUserBinding
import com.example.aplikasigithubuser.model.UserDetailModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    companion object {
        private val TAB_TITLES = arrayOf(
            "Tab 1",
            "Tab 2"
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var DataItem = intent.getSerializableExtra("DataUserItem") as UserDetailModel
        Log.d("DataUserItem", DataItem.toString())

        val userDetailPagerAdapter = UserDetailPagerAdapter(this)
        binding.viewPager.adapter = userDetailPagerAdapter

        val tabs = binding.tabs
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        supportActionBar?.elevation = 0f

        Glide.with(this).load(DataItem.avatar_url).into(binding.ivDetailUser)
    }
}