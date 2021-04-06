package com.app.dubaiculture.ui.postLogin.explore.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.utils.glideInstance
import com.app.dubaiculture.utils.setTextColorRes
import kotlinx.android.synthetic.main.attraction_title_list_item.view.*
import java.util.*

open class SingleSelectionAdapter(
    private val context: Context,
    employees: ArrayList<AttractionCategory>,
    val iface: InvokeListener,
) :
    RecyclerView.Adapter<SingleSelectionAdapter.SingleViewHolder?>() {
    private var attractions: ArrayList<AttractionCategory>

    init {
        this.attractions = employees
    }

    private var checkedPosition = 0
    fun setEmployees(employees: ArrayList<AttractionCategory>) {
        this.attractions = ArrayList<AttractionCategory>()
        this.attractions = employees
        notifyDataSetChanged()
    }


//    val itemCount: Int
//        get() = employees.size

    inner class SingleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(attractions: AttractionCategory) {
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
                if (checkedPosition == adapterPosition) {
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
                itemView.cardview.setCardBackgroundColor(Color.parseColor("#5E2E82"))
                if (attractions.icon!!.isNotEmpty()) {
                    itemView.imgInnerIcon.glideInstance(attractions.icon, true)
                        .into(itemView.imgInnerIcon)
                } else {
                    itemView.imgInnerIcon.setImageResource(R.drawable.calender_white)
                }
                if (checkedPosition != adapterPosition) {

                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                }
            })
        }
    }


    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        holder.bind(attractions[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.attraction_title_list_item, parent, false)
        return SingleViewHolder(view)
    }

    override fun getItemCount() = attractions.size

    interface InvokeListener {
        fun getRowClick(position: Int = 0)
    }
}