package com.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.databinding.SearchFilterListItemsBinding
import com.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.SelectedFilter
import com.dubaiculture.utils.AsyncCell
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class SearchFilterSelectedAdapter(val iface: RemoveHeaderItem? = null) :
    BaseRecyclerAdapter<SelectedFilter>() {

    var selectedItems: List<SelectedFilter>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class EventsListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventsListViewHolder(EventsListItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpEventsListViewHolder(
            holder as SearchFilterSelectedAdapter.EventsListViewHolder,
            position
        )
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
        holder: SearchFilterSelectedAdapter.EventsListViewHolder,
        position: Int,
    ) {
        (holder.itemView as EventsListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                try {
                    it.root.setOnClickListener {
                        YoYo.with(Techniques.RubberBand)
                            .duration(100)
                            .playOn(it)
                        iface!!.onItemRemove(position, selectedItems[position])
                    }
                    it.tvFilterText.text = selectedItems[position].title
                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }

    interface RemoveHeaderItem {
        fun onItemRemove(pos: Int, item: SelectedFilter)
    }
}