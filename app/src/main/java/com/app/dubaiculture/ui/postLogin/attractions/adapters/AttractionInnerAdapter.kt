package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.databinding.AttractionsCategoryItemCellBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.AsyncCell
import com.google.android.material.shape.CornerFamily


class AttractionInnerAdapter(
    private val rowClickListener: RowClickListener? = null,
    private val isArabic: Boolean,
) : BaseRecyclerAdapter<AttractionCategory>() {

    var attractions: List<AttractionCategory>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class AttractionViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionViewHolder(
            AttractionInnerItemCell(parent.context).apply { inflate() }
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AttractionViewHolder -> setUpAttractionViewHolder(holder, position)
        }
    }

    override fun getItemCount() = attractions.size

    private inner class AttractionInnerItemCell(context: Context) : AsyncCell(context, true) {
        var binding: AttractionsCategoryItemCellBinding? = null
        override val layoutId = R.layout.attractions_category_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = AttractionsCategoryItemCellBinding.bind(view)
            return view.rootView
        }
    }

    private fun setUpAttractionViewHolder(
        holder: AttractionInnerAdapter.AttractionViewHolder,
        position: Int,
    ) {
        (holder.itemView as AttractionInnerAdapter.AttractionInnerItemCell).bindWhenInflated {
            // red


            val radius = resources.getDimension(R.dimen.my_corner_radius)
            val zeroDp = resources.getDimension(R.dimen.my_corner_radius)
            holder.itemView.binding?.apply {
                attractionImage.setOnClickListener {
                    rowClickListener!!.rowClickListener(position)
                }
                if (isArabic)
                    attractionImage.shapeAppearanceModel =
                        attractionImage.shapeAppearanceModel
                            .toBuilder()
                            .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                            .setBottomRightCornerSize(zeroDp)
                            .build()
                else
                    attractionImage.shapeAppearanceModel =
                        attractionImage.shapeAppearanceModel
                            .toBuilder()
                            .setTopRightCorner(CornerFamily.ROUNDED, radius)
                            .setBottomLeftCornerSize(zeroDp)
                            .build()


            }


            holder.itemView.binding?.let {
                if (!attractions[position].color.isNullOrEmpty()) {
                    it.attractionImage.setCardBackgroundColor(Color.parseColor(attractions[position].color))
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        it.attractionImage.outlineSpotShadowColor =
                            Color.parseColor(attractions[position].color)
                    }
                }
//                it.attractions = attractions[position]
            }
        }

    }
}