package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.PopularServiceInnerItemCellBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class PopularServiceInnerAdapter:
    BaseRecyclerAdapter<BaseModel>() {

    var popularService: List<BaseModel>
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

    private inner class PopularServiceInnerItemCell(context: Context) : AsyncCell(context,true) {
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
//            holder.itemView.binding?.popularServices=popularService[position]
        }
    }

}
