package com.app.dubaiculture.ui.postLogin.attractions.adapters

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
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.AttractionHomeInnerListItemBinding
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.ui.postLogin.attractions.utils.AttractionFilterItem
import com.app.dubaiculture.utils.AsyncCell

class AttractionListScreenAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private lateinit var filterList:ArrayList<Attractions>

    private var filter:AttractionFilterItem?=null


     var onNothingFound: (() -> Unit)? = null

    fun search(s: String?, onNothingFound: (() -> Unit)?) {
        this.onNothingFound = onNothingFound
        filter?.filter(s)

    }
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
            filterList= ArrayList(attractions)
        }



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
      if (filter==null){
          filter= AttractionFilterItem(filterList,this)
      }
        return filter as AttractionFilterItem
    }

    private inner class AttractionListItemCell(context: Context) : AsyncCell(context) {
        var binding: AttractionListItemCellBinding? = null
        override val layoutId = R.layout.attraction_list_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = AttractionListItemCellBinding.bind(view)
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

                    it.attractions = attractions[position]

                }catch (ex:IndexOutOfBoundsException){

                }
            }
        }
    }
}