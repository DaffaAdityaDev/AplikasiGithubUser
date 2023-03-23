package com.example.aplikasigithubuser

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
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
    private lateinit var adapter: MainViewAdapter
    private var currentQuery: String = "arif"

    companion object {
        private const val QUERY_KEY = "query_key"

    }

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

        viewModel.userListResponse.observe(this) { response ->
            if (response.isNotEmpty() && response != null) {
                adapter.setData(response)
            } else {
                Log.d("Response", response.toString() + "Failed Main")
            }
        }

        if (savedInstanceState != null) {
            currentQuery = savedInstanceState.getString(QUERY_KEY, "arif")
        }

        viewModel.getPost(currentQuery)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search User"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                currentQuery = query
                viewModel.getPost(query)
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(QUERY_KEY, currentQuery)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentQuery = savedInstanceState.getString(QUERY_KEY, "arif")
    }
}

