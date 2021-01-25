package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.TestItem
import com.app.dubaiculture.databinding.AttractionsItemContainerCellBinding
import com.app.dubaiculture.databinding.LargeItemCellBinding
import com.app.dubaiculture.databinding.SmallItemCellBinding
import com.app.dubaiculture.utils.AsyncCell

class ExploreRecyclerAsyncAdapter internal constructor(private val items: List<TestItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewTypes.SMALL.type -> SmallItemViewHolder(SmallItemCell(parent.context).apply { inflate() })
            else -> LargeItemViewHolder(LargeItemCell(parent.context).apply { inflate() })
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SmallItemViewHolder -> setUpSmallViewHolder(holder, position)
            is LargeItemViewHolder -> setUpLargeViewHolder(holder, position)
        }
    }

    private fun setUpLargeViewHolder(holder: LargeItemViewHolder, position: Int) {
        (holder.itemView as LargeItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.item = item
            }
        }
    }

    private fun setUpSmallViewHolder(
        holder: SmallItemViewHolder,
        position: Int
    ) {
        (holder.itemView as SmallItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.item = item
            }
        }
    }

    private inner class LargeItemViewHolder(view: ViewGroup) :
        RecyclerView.ViewHolder(view)

    private inner class SmallItemViewHolder(view: ViewGroup) :
        RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int = when {
        items[position].code % 2 == 0 -> ViewTypes.LARGE.type
        else -> ViewTypes.SMALL.type
    }

    private inner class AttractionItemCell(context: Context) : AsyncCell(context) {
        var binding: AttractionsItemContainerCellBinding? = null
        override val layoutId=R.layout.attractions_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding= AttractionsItemContainerCellBinding.bind(view)
            return view.rootView
        }
    }

    private inner class LargeItemCell(context: Context) : AsyncCell(context) {
        var binding: LargeItemCellBinding? = null
        override val layoutId = R.layout.large_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = LargeItemCellBinding.bind(view)
            return view.rootView
        }
    }

    private inner class SmallItemCell(context: Context) : AsyncCell(context) {
        var binding: SmallItemCellBinding? = null
        override val layoutId = R.layout.small_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = SmallItemCellBinding.bind(view)
            return view.rootView
        }
    }

    enum class ViewTypes(val type: Int) {
        LARGE(0),
        SMALL(1),
        ATTRACTION(3),
        MUSTSEE(4),
        PLANYOURTRIP(5),
        UPCOMINGEVENTS(6),
        POPULARSERVICES(7),
        LATESTNEWS(8),
    }

}