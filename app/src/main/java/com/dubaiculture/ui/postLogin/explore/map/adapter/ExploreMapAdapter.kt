package com.dubaiculture.ui.postLogin.explore.map.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.explore.local.models.ExploreMap
import com.dubaiculture.databinding.ExploreNearItemsBinding
import com.dubaiculture.ui.postLogin.events.`interface`.DirectionClickListener
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.AsyncCell
import java.text.NumberFormat
import java.util.*

class ExploreMapAdapter(
    var isArabic: Boolean,
    var rowClickListener: RowClickListener,
    var directionClickListener: DirectionClickListener
)


    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<ExploreMap>() {
        override fun areItemsTheSame(oldItem: ExploreMap, newItem: ExploreMap): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ExploreMap, newItem: ExploreMap): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var explore: List<ExploreMap>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class ExploreNearMapViewHolder(view: ViewGroup) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExploreNearMapViewHolder(ExploreItemCell(parent.context).apply { inflate() })

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        setUpExploreNearMapViewHolder(
            holder = holder as ExploreMapAdapter.ExploreNearMapViewHolder,
            position
        )
    }

    override fun getItemCount() = explore.size

    //Data Binding
    private inner class ExploreItemCell(context: Context) : AsyncCell(context, true) {
        var binding: ExploreNearItemsBinding? = null
        override val layoutId = R.layout.explore_near_items
        override fun createDataBindingView(view: View): View? {
            binding = ExploreNearItemsBinding.bind(view)
            return view.rootView
        }
    }

    private fun setUpExploreNearMapViewHolder(
        holder: ExploreMapAdapter.ExploreNearMapViewHolder,
        position: Int,
    ) {
        (holder.itemView as ExploreMapAdapter.ExploreItemCell).bindWhenInflated {
            holder.itemView.binding?.let {
                arrowtRTL(isArabic, it.arrow)
                try {
                    it.cl.setOnClickListener {
                        rowClickListener.rowClickListener(position)
                    }
                    it.loc.setOnClickListener {
                        directionClickListener.directionClickListener(position)
                    }
                    val item = explore[position]
                    it.explore = item
                    if (isArabic) {
                        val nf: NumberFormat = NumberFormat.getInstance(Locale("ar"))
                        nf.maximumFractionDigits = 1
                        it.tvKm.text =
                            nf.format(item.distance) + " " + resources.getString(R.string.away)
                    } else {
                        val nf: NumberFormat = NumberFormat.getInstance(Locale("en"))
                        nf.maximumFractionDigits = 1
                        it.tvKm.text =
                            nf.format(item.distance) + " km"// + resources.getString(R.string.away)
                    }

                } catch (ex: IndexOutOfBoundsException) {
                    print(ex.stackTrace)
                }
            }
        }
    }


    fun arrowtRTL(isArabic: Boolean, img: ImageView) {
        when {
            isArabic -> {
                img.scaleX = -1f
            }
            else -> {
                img.scaleX = 1f
            }
        }
    }
}