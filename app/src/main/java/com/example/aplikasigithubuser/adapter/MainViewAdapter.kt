package com.example.aplikasigithubuser.adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aplikasigithubuser.DetailUserActivity
import com.example.aplikasigithubuser.databinding.ItemUserBinding
import com.example.aplikasigithubuser.model.GithubUserModel
import com.example.aplikasigithubuser.model.UserDetailModel
import java.io.Serializable

class MainViewAdapter(private val context: Context, private var DataSet: List<GithubUserModel>) : RecyclerView.Adapter<MainViewAdapter.MainViewHolder>() {

    fun setData(newData: List<Any>) {
        DataSet = newData as List<GithubUserModel>
        notifyDataSetChanged()
    }

    class MainViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvItemProfile: TextView = binding.tvItemProfile
        val ivItemProfile: ImageView = binding.IVItemProfile
        val clItemUser: ConstraintLayout = binding.clItemUser

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewAdapter.MainViewHolder, position: Int) {
        holder.tvItemProfile.text = DataSet[position].login.toString()
        Glide.with(context).load(DataSet[position].avatar_url).into(holder.ivItemProfile)
        holder.clItemUser.setOnClickListener {
            var intent = Intent(context, DetailUserActivity::class.java)
            var data = UserDetailModel(
                login = DataSet[position].login,
                id = DataSet[position].id,
                avatar_url = DataSet[position].avatar_url,
                followers_url = DataSet[position].followers_url,
            )
            intent.putExtra("DataUserItem", data as Serializable)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return DataSet.size
    }
}