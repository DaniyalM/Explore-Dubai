package com.app.dubaiculture.ui.postLogin.latestnews.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R

class NewsArticleAdapter (val mlist: List<String>) : RecyclerView.Adapter<NewsArticleAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  = MyViewHolder(parent)

    override fun onBindViewHolder(holder: NewsArticleAdapter.MyViewHolder, position: Int) {
            val articles = mlist[position]
                holder.tvArticle.text = articles
    }

    override fun getItemCount()= mlist.size

    inner class MyViewHolder(parent: ViewGroup) :RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.item_news_article, parent, false)){
        val context = itemView.context!!
        val tvArticle = itemView.findViewById<TextView>(R.id.tv_title_category)!!

    }
}