package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.databinding.SearchFilterListItemsBinding
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.SelectedFilter
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class SelectedFiltersListAdapter(val iface: RemoveHeaderItem) :
    ListAdapter<SelectedFilter, SelectedFiltersListAdapter.NewSelectedItemCellViewHolder>(object :
        DiffUtil.ItemCallback<SelectedFilter>() {
        override fun areItemsTheSame(oldItem: SelectedFilter, newItem: SelectedFilter): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: SelectedFilter, newItem: SelectedFilter): Boolean {
            return oldItem.title == newItem.title
        }

    }) {

//    inner class NewsListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =

        NewSelectedItemCellViewHolder(
            SearchFilterListItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            iface = iface
        )


    override fun onBindViewHolder(holder: NewSelectedItemCellViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class NewSelectedItemCellViewHolder(
        val binding: SearchFilterListItemsBinding,
        val iface: RemoveHeaderItem

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(selectedFilter: SelectedFilter) {
            try {
                binding.root.setOnClickListener {
                    YoYo.with(Techniques.RubberBand)
                        .duration(100)
                        .playOn(it)
                    iface.onItemRemove(selectedFilter)
                }
                binding.tvFilterText.text = selectedFilter.title
            } catch (ex: IndexOutOfBoundsException) {
                print(ex.stackTrace)
            }
        }
    }

    interface RemoveHeaderItem {
        fun onItemRemove(item: SelectedFilter)
    }

}