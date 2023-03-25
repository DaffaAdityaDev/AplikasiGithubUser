package com.example.githubuser.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.DetailUser
import com.example.githubuser.ItemsItem
import com.example.githubuser.R
import com.example.githubuser.databinding.UserItemBinding
import com.google.android.material.snackbar.Snackbar


class UserAdapter(private val listUser: List<ItemsItem>, private val context: Context) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvUserName.text = user.login.toString()
            Log.d("TAG", "bind: ${user.avatarUrl}")
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .override(100, 100)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.ivUserItem)

            binding.tvGithubUrl.text = user.htmlUrl.toString()

            binding.root.setOnClickListener {
                var intent = Intent(context, DetailUser::class.java)
                intent.putExtra("username", user.login.toString())
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
}
