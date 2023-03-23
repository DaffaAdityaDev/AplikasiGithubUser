package com.example.aplikasigithubuser.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aplikasigithubuser.MainViewModel
import com.example.aplikasigithubuser.R

import com.example.aplikasigithubuser.model.UserDetailModel

class UserDetailPagerAdapter(
    activity: AppCompatActivity,
    dataItem: UserDetailModel) : FragmentStateAdapter(activity) {

    private var dataItem: UserDetailModel = dataItem

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = followFragment(dataItem)
        fragment.arguments = Bundle().apply {
            putInt(followFragment.ARG_SECTION_NUMBER, position + 1)
        }

        return fragment as Fragment
    }
}

class followFragment(dataItem: UserDetailModel) : Fragment() {

    private var dataItem: UserDetailModel = dataItem
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvLabel: TextView = view.findViewById(R.id.section_label)
        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)


        if (index == 1) {
            tvLabel.text = "2222"
        } else {
            tvLabel.text = "23231"
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }
}