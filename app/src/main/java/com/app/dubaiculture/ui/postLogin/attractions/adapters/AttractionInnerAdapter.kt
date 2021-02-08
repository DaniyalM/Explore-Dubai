package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.AttractionsItemCellBinding
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class AttractionInnerAdapter(val glide: RequestManager) :
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

    var attractions: List<BaseModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class AttractionViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionViewHolder(
            AttractionInnerItemCell(parent.context).apply { inflate() }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AttractionViewHolder -> setUpAttractionViewHolder(holder,position)
        }
//        val attraction = attractions[position]
//        holder.itemView.apply {
//          //  glide.load(attraction.image_url).into(attraction_image)
////            glide.load(attraction.image).into(attraction_image)
//            attraction_title_text.text = attraction.title
//        }
    }

    override fun getItemCount() = attractions.size

    private inner class AttractionInnerItemCell(context: Context) : AsyncCell(context,true) {
        var binding: AttractionsItemCellBinding? = null
        override val layoutId = R.layout.attractions_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = AttractionsItemCellBinding.bind(view)
            return view.rootView
        }
    }

    private fun setUpAttractionViewHolder(
        holder: AttractionInnerAdapter.AttractionViewHolder,
        position: Int
    ) {
        (holder.itemView as AttractionInnerAdapter.AttractionInnerItemCell).bindWhenInflated {
            holder.itemView.binding?.attractions=attractions[position]
        }
    }


}