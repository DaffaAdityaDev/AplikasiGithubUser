package com.example.aplikasigithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithubuser.adapter.MainViewAdapter
import com.example.aplikasigithubuser.adapter.UserDetailPagerAdapter
import com.example.aplikasigithubuser.databinding.ActivityMainBinding
import com.example.aplikasigithubuser.repository.Repository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MainViewAdapter(this,emptyList())
        binding.rvItemGithub.layoutManager = LinearLayoutManager(this)
        binding.rvItemGithub.adapter = adapter

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.userListResponse.observe(
            this,
        ) { response ->
            if(response.isNotEmpty()) {
                adapter.setData(response)
            } else {
                Log.d("Response", response.toString())
            }

        }
        viewModel.getPost()

//        viewModel.myResponse.observe(this) { response ->
//            if (response.isSuccessful) {
//                val data = response.body()?.map { it.id.toString() } ?: emptyList()
//                Log.d("Response", "Data received: $data")
//
//            } else {
//                Log.d("Response", response.errorBody().toString())
//            }
//        }

    }
}

