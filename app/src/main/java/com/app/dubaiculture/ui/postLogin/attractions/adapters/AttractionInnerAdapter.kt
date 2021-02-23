package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.AttractionsItemCellBinding
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.AsyncCell
import com.bumptech.glide.RequestManager
import com.google.android.material.shape.CornerFamily
import kotlinx.android.synthetic.main.attractions_item_cell.view.*


class AttractionInnerAdapter(
    val glide: RequestManager,
    val context: Context,
    private val isArabic: Boolean,
) : BaseRecyclerAdapter() {

    var attractions: List<BaseModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    val allColors = context.resources.getStringArray(R.array.colors)


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
        var binding: AttractionsItemCellBinding? = null
        override val layoutId = R.layout.attractions_item_cell
        override fun createDataBindingView(view: View): View? {
            binding = AttractionsItemCellBinding.bind(view)
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
            if (isArabic)
                holder.itemView.binding?.attractionImage?.shapeAppearanceModel =
                    holder.itemView.binding?.attractionImage!!.shapeAppearanceModel
                        .toBuilder()
                        .setTopLeftCorner(CornerFamily.ROUNDED, radius)
                        .setBottomRightCornerSize(zeroDp)
                        .build()
             else
                holder.itemView.binding?.attractionImage?.shapeAppearanceModel =
                    holder.itemView.binding?.attractionImage!!.shapeAppearanceModel
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED, radius)
                        .setBottomLeftCornerSize(zeroDp)
                        .build()
                holder.itemView.binding?.let {
                    if (position < allColors.size) {
                        it.attractionImage.setCardBackgroundColor(Color.parseColor(allColors[position]))
                    }
                    it.attractions = attractions[position]
                }
            }

        }
    }