package com.app.dubaiculture.ui.postLogin.latestnews.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.LatestNewsInnerItemCellBinding
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class LatestNewsInnerAdapter (val glide: RequestManager) :
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BaseModel,
            newItem: BaseModel
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var latestNews: List<BaseModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class LatestNewsViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LatestNewsViewHolder(LatestNewsInnerItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is LatestNewsViewHolder -> setUpLatestNewsViewHolder(holder, position)
        }
//        val latestNews = latestNews[position]
//        holder.itemView.apply {
////            glide.load(mustSee.image_url).into(attraction_image)
//        }
    }

    override fun getItemCount() = latestNews.size


    private inner class LatestNewsInnerItemCell(context: Context) : AsyncCell(context,true) {
        var binding: LatestNewsInnerItemCellBinding? = null
        override val layoutId = R.layout.latest_news_inner_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = LatestNewsInnerItemCellBinding.bind(view)
            return view.rootView
        }
    }


    private fun setUpLatestNewsViewHolder(
        holder: LatestNewsInnerAdapter.LatestNewsViewHolder,
        position: Int
    ) {
        (holder.itemView as LatestNewsInnerAdapter.LatestNewsInnerItemCell).bindWhenInflated {
            latestNews[position].let { item ->
//                holder.itemView.binding?.innerSectionRv?.let {
//
//                }
            }
        }
    }
}