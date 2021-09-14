package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.SearchFilterListItemsBinding
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.SelectedFilter
import com.app.dubaiculture.utils.AsyncCell
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo

class SelectedFiltersListAdapter(val iface: RemoveHeaderItem? = null) : ListAdapter<SelectedFilter, SelectedFiltersListAdapter.NewsListViewHolder>(object :
    DiffUtil.ItemCallback<SelectedFilter>(){
    override fun areItemsTheSame(oldItem: SelectedFilter, newItem: SelectedFilter): Boolean {
       return oldItem.hashCode()==newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: SelectedFilter, newItem: SelectedFilter): Boolean {
       return oldItem.title==oldItem.title
    }

}) {

    inner class NewsListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NewsListViewHolder(NewsListItemCell(parent.context).apply { inflate() })



    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        setUpEventsListViewHolder(holder ,position)
    }

    private inner class NewsListItemCell(context: Context) : AsyncCell(context, true) {
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
        holder: NewsListViewHolder,
        position: Int,
    ) {
        (holder.itemView as SelectedFiltersListAdapter.NewsListItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                try {
                    it.root.setOnClickListener {
                        YoYo.with(Techniques.RubberBand)
                            .duration(100)
                            .playOn(it)
                        iface!!.onItemRemove(position, getItem(position))
                    }
                    it.tvFilterText.text = getItem(position).title
                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }

    interface RemoveHeaderItem {
        fun onItemRemove(pos: Int, item:SelectedFilter)
    }


}