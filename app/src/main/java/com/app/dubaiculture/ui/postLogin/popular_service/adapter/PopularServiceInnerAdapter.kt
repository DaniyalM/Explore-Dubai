package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.PopularServices
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.popular_service_inner_item_cell.view.*

class PopularServiceInnerAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<PopularServices>() {
        override fun areItemsTheSame(oldItem: PopularServices, newItem: PopularServices): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PopularServices,
            newItem: PopularServices
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var popularService: List<PopularServices>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class PopularServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PopularServiceViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.popular_service_inner_item_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val popularService = popularService[position]
        holder.itemView.apply {
//            glide.load(mustSee.image_url).into(attraction_image)
            tv_title.text = popularService.title
        }
    }

    override fun getItemCount() = popularService.size

}
