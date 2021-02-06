package com.app.dubaiculture.ui.postLogin.explore.mustsee.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.MustSeeInnerItemCellBinding
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class MustSeeInnerAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var mustSees: List<BaseModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)


    inner class MustSeeViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MustSeeViewHolder(MustSeeInnerItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MustSeeViewHolder -> setUpMustSeeViewHolder(holder, position)
        }
//        val mustSee = mustSees[position]
//        holder.itemView.apply {
////            glide.load(mustSee.image_url).into(attraction_image)
//            category.text = mustSee.title
//            tv_heritage_title.text = mustSee.title
//        }
    }

    override fun getItemCount() = mustSees.size


    private inner class MustSeeInnerItemCell(context: Context) : AsyncCell(context,true) {
        var binding: MustSeeInnerItemCellBinding? = null
        override val layoutId = R.layout.must_see_inner_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = MustSeeInnerItemCellBinding.bind(view)
            return view.rootView
        }
    }


    private fun setUpMustSeeViewHolder(
        holder: MustSeeInnerAdapter.MustSeeViewHolder,
        position: Int
    ) {
        (holder.itemView as MustSeeInnerAdapter.MustSeeInnerItemCell).bindWhenInflated {
            mustSees[position].let { item ->
//                holder.itemView.binding?.innerSectionRv?.let {
//
//                }
            }
        }
    }


}