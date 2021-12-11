package com.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.dubaiculture.utils.getColorFromAttr
import com.dubaiculture.utils.glideInstance
import com.dubaiculture.utils.setTextColorRes
import com.google.android.material.card.MaterialCardView

open class SingleSelectionAdapter(
    private val context: Context,
    val iface: InvokeListener,
) :
    BaseRecyclerAdapter<AttractionCategory>() {

    var attractions: List<AttractionCategory>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private var checkedPosition = 0

    inner class SingleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(attractions: AttractionCategory) {
            val imageView = itemView.findViewById<ImageView>(R.id.imgInnerIcon)
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
            val materialCardView = itemView.findViewById<MaterialCardView>(R.id.cardview)

            if (absoluteAdapterPosition != 0) {
                imageView.visibility = View.VISIBLE

            } else {
                imageView.visibility = View.GONE

            }


            if (attractions.icon!!.isNotEmpty()) {
                imageView.glideInstance(attractions.selectedSvg, true)
                    .into(imageView)
            } else {
                imageView.setImageResource(R.drawable.calender)
            }
            if (checkedPosition == -1) {
//                imageView.visibility = View.GONE
                // un selected

                itemView.findViewById<TextView>(R.id.tv_title).setTextColorRes(R.color.black_200)
                itemView.findViewById<MaterialCardView>(R.id.cardview)
                    .setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                if (checkedPosition == absoluteAdapterPosition) {
                    //  selected
                    tvTitle.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white_900
                        )
                    )
                    materialCardView.setCardBackgroundColor(Color.parseColor("#5E2E82"))
//                    imageView.visibility = View.VISIBLE
                } else {
                    // un selected
                    tvTitle.setTextColor(context.getColorFromAttr(R.attr.colorSecondary))
                    materialCardView.setCardBackgroundColor(context.getColorFromAttr(R.attr.colorSurface))
//                    imageView.visibility = View.GONE
                }
            }
            tvTitle.text = attractions.title
            itemView.setOnClickListener(View.OnClickListener {
                // un selected
                iface.getRowClick(category = attractions)
                tvTitle.setTextColor(ContextCompat.getColor(itemView.context, R.color.white_900))


                if (attractions.color.isNullOrEmpty()) {
                    materialCardView.setCardBackgroundColor(Color.parseColor("#5E2E82"))
                } else {
                    materialCardView.setCardBackgroundColor(Color.parseColor(attractions.color))
                }
                if (attractions.icon!!.isNotEmpty()) {
                    imageView.glideInstance(attractions.icon, true)
                        .into(imageView)
                } else {
                    imageView.setImageResource(R.drawable.calender_white)
                }
                if (checkedPosition != absoluteAdapterPosition) {

                    notifyItemChanged(checkedPosition)
                    checkedPosition = absoluteAdapterPosition
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.explore_map_layout_headers, parent, false)
        return SingleViewHolder(view)
    }

    override fun getItemCount() = attractions.size

    interface InvokeListener {
        fun getRowClick(category: AttractionCategory)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val singleViewHolder = holder as SingleViewHolder
        singleViewHolder.bind(attractions[position])

    }
}