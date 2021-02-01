package com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.MustSee
import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.attractions_item_cell.view.*
import kotlinx.android.synthetic.main.must_see_inner_item_cell.view.*

class MustSeeInnerAdapter (val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<MustSee>() {
        override fun areItemsTheSame(oldItem: MustSee, newItem: MustSee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MustSee, newItem: MustSee): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var mustSees: List<MustSee>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class MustSeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MustSeeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.must_see_inner_item_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mustSee = mustSees[position]
        holder.itemView.apply {
//            glide.load(mustSee.image_url).into(attraction_image)
            category.text = mustSee.title
            tv_heritage_title.text = mustSee.title
        }
    }

    override fun getItemCount() = mustSees.size



}