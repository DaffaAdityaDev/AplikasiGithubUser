package com.example.githubuser.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.ItemsItem
import com.example.githubuser.adapter.DetailFollowAdapter
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.databinding.FragmentFollowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvFollow: RecyclerView
    private lateinit var detailFollowAdapter: DetailFollowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentFollowBinding.inflate(inflater, container, false)

        rvFollow = binding.rvDetailUserFollow
        rvFollow.layoutManager = LinearLayoutManager(context)
        detailFollowAdapter = DetailFollowAdapter()
        rvFollow.adapter = detailFollowAdapter

        return binding.root
    }

    // get data from api and call, setLoading Status and call setListUsersData if response is success
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val username = arguments?.getString(ARG_USERNAME)

        if (index == 0) {
            getFollower(username.toString(), "followers")
        } else {
            getFollower(username.toString(), "following")

        }

    }

    // get data from api and call, setLoading Status and call setListUsersData if response is success
    private fun getFollower(username : String, pathFollow : String) {

        if (pathFollow == "followers") {
            getCient(username, pathFollow)
        } else {
            getCient(username, pathFollow)
        }
    }

    // set data to adapter
    private fun setListFollowData(itemList : List<ItemsItem>) {
        val listFollowData = ArrayList<ItemsItem>()
        listFollowData.addAll(itemList)
        Log.d("FollowFragment", "setListFollowData: $listFollowData")

        val userAdapter = UserAdapter(listFollowData, requireContext())
        binding.rvDetailUserFollow.adapter = userAdapter
    }

    // get data from api and call, setLoading Status and call setListUsersData if response is success
    // if response is not success, show error message
    private fun getCient(username : String, pathFollow : String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollow(username, pathFollow)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val itemList = response.body()
                    if (itemList != null) {
                        showLoading(false)
                        setListFollowData(itemList)
                    }
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // companion object for passing data
    companion object {
        private const val TAG = "FollowFragment"
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_USERNAME = "username"
    }

}
