package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.TestItem
import com.app.dubaiculture.databinding.AttractionsItemContainerCellBinding
import com.app.dubaiculture.databinding.LargeItemCellBinding
import com.app.dubaiculture.databinding.SmallItemCellBinding
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionInnerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager

class ExploreRecyclerAsyncAdapter internal constructor(
    private val items: List<TestItem>,
    private val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var glide: RequestManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ViewTypes.ATTRACTION.type -> AttractionViewHolder(AttractionItemCell(parent.context).apply { inflate() })
            else -> LargeItemViewHolder(LargeItemCell(parent.context).apply { inflate() })
        }

    override fun getItemCount(): Int = items.size

    fun provideGlideInstance(glide: RequestManager) {
        this.glide = glide
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AttractionViewHolder -> setUpAttractionViewHolder(holder, position)
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

//    private fun setUpSmallViewHolder(
//        holder: SmallItemViewHolder,
//        position: Int
//    ) {
//        (holder.itemView as SmallItemCell).bindWhenInflated {
//            items[position].let { item ->
//                holder.itemView.binding?.item = item
//            }
//        }
//    }

    private fun setUpAttractionViewHolder(holder: AttractionViewHolder, position: Int) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val attractionInnerAdapter =AttractionInnerAdapter(glide)
        (holder.itemView as AttractionItemCell).bindWhenInflated {
            items[position].let { item ->
                holder.itemView.binding?.innerAttractionRv?.let {
                    it.layoutManager = layoutManager
                    it.adapter = attractionInnerAdapter
                    attractionInnerAdapter.attractions=item.attractions
                }
            }
        }
    }

    private inner class LargeItemViewHolder(view: ViewGroup) :
        RecyclerView.ViewHolder(view)

    private inner class SmallItemViewHolder(view: ViewGroup) :
        RecyclerView.ViewHolder(view)

    private inner class AttractionViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int = when {
        items[position].code % 2 == 0 -> ViewTypes.ATTRACTION.type
        else -> ViewTypes.LARGE.type
    }

    private inner class AttractionItemCell(context: Context) : AsyncCell(context) {
        var binding: AttractionsItemContainerCellBinding? = null
        override val layoutId = R.layout.attractions_item_container_cell
        override fun createDataBindingView(view: View): View? {
            binding = AttractionsItemContainerCellBinding.bind(view)
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
//        LARGE(0),
//        SMALL(1),
        ATTRACTION(0),
        LARGE(1),
//        MUSTSEE(2),
        PLANYOURTRIP(3),
        UPCOMINGEVENTS(4),
        POPULARSERVICES(5),
        LATESTNEWS(6),
    }

}