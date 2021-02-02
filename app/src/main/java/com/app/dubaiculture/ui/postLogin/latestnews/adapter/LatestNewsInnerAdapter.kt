package com.app.dubaiculture.ui.postLogin.latestnews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.LatestNews
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.latest_news_inner_item_cell.view.*

class LatestNewsInnerAdapter (val glide: RequestManager) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<LatestNews>() {
        override fun areItemsTheSame(oldItem: LatestNews, newItem: LatestNews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LatestNews,
            newItem: LatestNews
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var latestNews: List<LatestNews>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class PopularServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PopularServiceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.latest_news_inner_item_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val latestNews = latestNews[position]
        holder.itemView.apply {
//            glide.load(mustSee.image_url).into(attraction_image)
        }
    }

    override fun getItemCount() = latestNews.size


}