package com.example.aplikasigithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasigithubuser.R
import com.example.aplikasigithubuser.model.GithubUserModel

class MainViewAdapter(private var DataSet: List<GithubUserModel>) : RecyclerView.Adapter<MainViewAdapter.MainViewHolder>() {

    fun setData(newData: List<Any>) {
        DataSet = newData as List<GithubUserModel>
        notifyDataSetChanged()
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.text_view)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewAdapter.MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewAdapter.MainViewHolder, position: Int) {
        holder.textView.text = DataSet[position].id.toString()
    }

    override fun getItemCount(): Int {
        return DataSet.size
    }
}