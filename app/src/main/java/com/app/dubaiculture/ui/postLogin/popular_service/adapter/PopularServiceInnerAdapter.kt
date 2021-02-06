package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.InnerValue
import com.app.dubaiculture.databinding.LatestNewsInnerItemCellBinding
import com.app.dubaiculture.databinding.PopularServiceInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.LatestNewsInnerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.popular_service_inner_item_cell.view.*

class PopularServiceInnerAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<InnerValue>() {
        override fun areItemsTheSame(oldItem: InnerValue, newItem: InnerValue): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: InnerValue,
            newItem: InnerValue
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var popularService: List<InnerValue>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class PopularServiceViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PopularServiceViewHolder(PopularServiceInnerItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PopularServiceViewHolder ->  setUpPopularServiceViewHolder(holder, position)
        }
//        val popularService = popularService[position]
//        holder.itemView.apply {
////            glide.load(mustSee.image_url).into(attraction_image)
//            tv_title.text = popularService.title
//        }
    }

    override fun getItemCount() = popularService.size

    private inner class PopularServiceInnerItemCell(context: Context) : AsyncCell(context,450) {
        var binding: PopularServiceInnerItemCellBinding? = null
        override val layoutId = R.layout.popular_service_inner_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = PopularServiceInnerItemCellBinding.bind(view)
            return view.rootView
        }
    }


    private fun setUpPopularServiceViewHolder(
        holder: PopularServiceInnerAdapter.PopularServiceViewHolder,
        position: Int
    ) {
        (holder.itemView as PopularServiceInnerAdapter.PopularServiceInnerItemCell).bindWhenInflated {
            popularService[position].let { item ->
//                holder.itemView.binding?.innerSectionRv?.let {
//
//                }
            }
        }
    }

}
