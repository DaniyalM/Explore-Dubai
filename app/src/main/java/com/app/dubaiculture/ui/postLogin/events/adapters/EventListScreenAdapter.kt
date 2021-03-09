package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.ui.postLogin.attractions.utils.AttractionFilterItem
import com.app.dubaiculture.utils.AsyncCell

class EventListScreenAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Attractions>() {
        override fun areItemsTheSame(oldItem: Attractions, newItem: Attractions): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Attractions, newItem: Attractions): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var attractions: List<Attractions>
        get() = differ.currentList
        set(value){
            differ.submitList(value)
        }
    inner class AttractionListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionListViewHolder(AttractionListItemCell(parent.context).apply { inflate() })
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpAttractionListViewHolder(holder as EventListScreenAdapter.AttractionListViewHolder,
            position)
    }
    override fun getItemCount() = attractions.size
    private inner class AttractionListItemCell(context: Context) : AsyncCell(context) {
        var binding: AttractionListItemCellBinding? = null
        override val layoutId = R.layout.attraction_list_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = AttractionListItemCellBinding.bind(view)
            return view.rootView
        }
    }
    private fun setUpAttractionListViewHolder(
        holder: EventListScreenAdapter.AttractionListViewHolder,
        position: Int,
    ) {
        (holder.itemView as AttractionListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                try {
                    it.attractions = attractions[position]
                }catch (ex:IndexOutOfBoundsException){
                }
            }
        }
    }
}