package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R

class DetailFollowAdapter : RecyclerView.Adapter<DetailFollowAdapter.DetailFollowHolder>() {
    // Implement required methods here
    class DetailFollowHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Add view references here
        fun bind() {
            // Add binding code here
        }
    }

    private val items = listOf("Item 1", "Item 2", "Item 3")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailFollowHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_follow, parent, false)
        return DetailFollowHolder(view)
    }

    override fun onBindViewHolder(holder: DetailFollowHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = items.size
}