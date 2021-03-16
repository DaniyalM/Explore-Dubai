package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.filter.models.Filter
import com.app.dubaiculture.databinding.SearchFilterListItemsBinding
import com.app.dubaiculture.utils.AsyncCell

class FilterHeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Filter>() {
        override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return oldItem.userID == newItem.userID
        }

        override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var filterData: List<Filter>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }
    inner class FilterListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilterListViewHolder(FilterListItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpEventNearMapViewHolder(holder = holder as FilterHeaderAdapter.FilterListViewHolder,
            position)
    }

    override fun getItemCount() =filterData.size

    //Data Binding
    private inner class FilterListItemCell(context: Context) : AsyncCell(context) {
        var binding: SearchFilterListItemsBinding? = null
        override val layoutId = R.layout.search_filter_list_items
        override fun createDataBindingView(view: View): View? {
            binding = SearchFilterListItemsBinding.bind(view)
            return view.rootView
        }
    }

    private fun setUpEventNearMapViewHolder(
        holder: FilterHeaderAdapter.FilterListViewHolder,
        position: Int,
    ) {
        (holder.itemView as FilterHeaderAdapter.FilterListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                try {

                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }
}