package com.example.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UserAdapater(private val listUser: List<String>) : RecyclerView.Adapter<UserAdapater.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItem: TextView = itemView.findViewById(R.id.tvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapater.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapater.ViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvItem.text = user
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}