package com.app.dubaiculture.ui.postLogin.events.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.filter.models.SelectedItems
import com.app.dubaiculture.databinding.SearchFilterListItemsBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class FilterHeaderAdapter(val iface: RemoveHeaderItem? = null) :
    BaseRecyclerAdapter<SelectedItems>() {

    var selectedItems: List<SelectedItems>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class EventsListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventsListViewHolder(EventsListItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpEventsListViewHolder(holder as FilterHeaderAdapter.EventsListViewHolder,
            position)
    }

    override fun getItemCount() = selectedItems.size

    //Data Binding
    private inner class EventsListItemCell(context: Context) : AsyncCell(context, true) {
        var binding: SearchFilterListItemsBinding? = null
        override val layoutId = R.layout.search_filter_list_items
        override fun createDataBindingView(view: View): View? {
            binding = SearchFilterListItemsBinding.bind(view)
            YoYo.with(Techniques.BounceInDown)
                .duration(700)
                .playOn(binding?.root)
            return view.rootView
        }
    }

    private fun setUpEventsListViewHolder(
        holder: FilterHeaderAdapter.EventsListViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventsListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                try {
                    it.root.setOnClickListener {
                        YoYo.with(Techniques.RubberBand)
                            .duration(100)
                            .playOn(it)
                        iface!!.onItemRemove(position, selectedItems)
                    }
                    if (selectedItems[position].category!!.isNotEmpty()) {
                        it.tvFilterText.text = selectedItems[position].category
                    }
                    if (selectedItems[position].keyword!!.isNotEmpty()) {
                        it.tvFilterText.text = selectedItems[position].keyword

                    }
                    if (selectedItems[position].location!!.isNotEmpty()) {
                        it.tvFilterText.text = selectedItems[position].location

                    }
                    if (selectedItems[position].dateFrom!!.isNotEmpty()) {
                        it.tvFilterText.text = selectedItems[position].dateFrom

                    }
                    if (selectedItems[position].dateTo!!.isNotEmpty()) {
                        it.tvFilterText.text = selectedItems[position].dateTo

                    }
                    if (selectedItems[position].type!!.isNotEmpty()) {
                        it.tvFilterText.text = selectedItems[position].type

                    }


//                    it.filterData = selectedItems[position]
                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }

    interface RemoveHeaderItem {
        fun onItemRemove(pos: Int, list: List<SelectedItems>)
    }
}