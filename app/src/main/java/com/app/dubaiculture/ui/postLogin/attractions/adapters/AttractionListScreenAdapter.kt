package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.AttractionListItemBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.AsyncCell

class AttractionListScreenAdapter : BaseRecyclerAdapter() {



    inner class AttractionListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionListViewHolder(AttractionListItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpAttractionListViewHolder(holder as AttractionListScreenAdapter.AttractionListViewHolder,
            position)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    private inner class AttractionListItemCell(context: Context) : AsyncCell(context) {
        var binding: AttractionListItemBinding? = null
        override val layoutId = R.layout.attraction_list_item
        override fun createDataBindingView(view: View): View? {
            binding = AttractionListItemBinding.bind(view)
            return view.rootView
        }
    }

    private fun setUpAttractionListViewHolder(
        holder: AttractionListScreenAdapter.AttractionListViewHolder,
        position: Int,
    ) {
        (holder.itemView as AttractionListItemCell).bindWhenInflated {
            // red
            holder.itemView.binding?.let {
//                if (position<=allColors.size){
//                    it.attractionImage.setCardBackgroundColor(Color.parseColor(allColors[position]))
//                }
//                it.attractions=attractions[position]
            }
        }
    }
}