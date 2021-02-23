package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.AttractionListItemBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.AsyncCell

class AttractionListScreenAdapter : BaseRecyclerAdapter(), Filterable {
    private var onNothingFound: (() -> Unit)? = null

    fun search(s: String?, onNothingFound: (() -> Unit)?) {
        this.onNothingFound = onNothingFound
        filter.filter(s)

    }

    var attractions: List<BaseModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    var searchableAttractions: ArrayList<BaseModel> =  ArrayList()

    inner class AttractionListViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionListViewHolder(AttractionListItemCell(parent.context).apply { inflate() })
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpAttractionListViewHolder(holder as AttractionListScreenAdapter.AttractionListViewHolder,
            position)
    }

    override fun getItemCount() = attractions.size
    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                if (!constraint.isNullOrEmpty()){
                    val searchResults = attractions.filter { it.title?.equals(constraint) == true }
                    searchableAttractions.addAll(searchResults)
                }
//                if (constraint.isNullOrBlank()) {
//                    searchableAttractions.removeAll(attractions)
//                    searchableAttractions.addAll(attractions)
//                } else {
//
//                }
                return filterResults.also {
                    it.values = searchableAttractions
                    if (searchableAttractions.size>0){
                        searchableAttractions.removeAll(searchableAttractions)
                    }

                }


            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if (searchableAttractions.isNullOrEmpty())
                    onNothingFound?.invoke()
                notifyDataSetChanged()
            }

        }
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
            holder.itemView.binding?.let {
                try {

                    it.attractions = searchableAttractions[position]

                }catch (ex:IndexOutOfBoundsException){

                }
            }
        }
    }
}