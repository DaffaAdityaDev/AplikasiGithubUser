package com.example.aplikasigithubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aplikasigithubuser.fragment.FollowerFragment
import com.example.aplikasigithubuser.fragment.FollowingFragment

class UserDetailPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment? = null
        when (position) {
            0 -> return FollowerFragment()
            1 -> return FollowingFragment()
        }
        return fragment as Fragment
    }
}