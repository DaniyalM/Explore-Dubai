package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.ui.base.recyclerstuf.BaseRecyclerAdapter
import com.app.dubaiculture.utils.glideInstance
import com.app.dubaiculture.utils.setTextColorRes
import kotlinx.android.synthetic.main.explore_map_layout_headers.view.*
import java.util.*

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
            if (absoluteAdapterPosition != 0) {
                itemView.imgInnerIcon.visibility = View.VISIBLE

            } else {
                itemView.imgInnerIcon.visibility = View.GONE

            }


            if (attractions.icon!!.isNotEmpty()) {
                itemView.imgInnerIcon.glideInstance(attractions.selectedSvg, true)
                        .into(itemView.imgInnerIcon)
            } else {
                itemView.imgInnerIcon.setImageResource(R.drawable.calender)
            }
            if (checkedPosition == -1) {
//                imageView.visibility = View.GONE
                // un selected

                itemView.tv_title.setTextColorRes(R.color.black_200)
                itemView.cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                if (checkedPosition == absoluteAdapterPosition) {
                    //  selected
                    itemView.tv_title.setTextColorRes(R.color.white_900)
                    itemView.cardview.setCardBackgroundColor(Color.parseColor("#5E2E82"))
//                    imageView.visibility = View.VISIBLE
                } else {
                    // un selected
                    itemView.tv_title.setTextColorRes(R.color.black_200)
                    itemView.cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
//                    imageView.visibility = View.GONE
                }
            }
            itemView.tv_title.text = attractions.title
            itemView.setOnClickListener(View.OnClickListener {
                // un selected
                iface.getRowClick(absoluteAdapterPosition)
                itemView.tv_title.setTextColorRes(R.color.white_900)

                if (attractions.color.isNullOrEmpty()) {
                    itemView.cardview.setCardBackgroundColor(Color.parseColor("#5E2E82"))
                } else {
                    itemView.cardview.setCardBackgroundColor(Color.parseColor(attractions.color))
                }
                if (attractions.icon!!.isNotEmpty()) {
                    itemView.imgInnerIcon.glideInstance(attractions.icon, true)
                            .into(itemView.imgInnerIcon)
                } else {
                    itemView.imgInnerIcon.setImageResource(R.drawable.calender_white)
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
        fun getRowClick(position: Int = 0)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val singleViewHolder = holder as SingleViewHolder
        singleViewHolder.bind(attractions[position])

    }
}