package com.app.dubaiculture.ui.postLogin.events.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Attraction
import com.app.dubaiculture.data.repository.explore.local.models.UpComingEvents
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.attractions_item_cell.view.*

class UpComingEventsInnerAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<UpComingEvents>() {
        override fun areItemsTheSame(oldItem:UpComingEvents, newItem: UpComingEvents): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UpComingEvents, newItem: UpComingEvents): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var upComingEvents: List<UpComingEvents>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class UpComingEventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UpComingEventsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.upcoming_events_item_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val upComingEvents = upComingEvents[position]
        holder.itemView.apply {
//            glide.load(upComingEvents.image_url).into(attraction_image)
            attraction_title_text.text = upComingEvents.title
        }
    }

    override fun getItemCount() = upComingEvents.size

//    private inner class AttractionInnerItemCell(context: Context) : AsyncCell(context) {
//        var binding: AttractionsItemCellBinding? = null
//        override val layoutId = R.layout.attractions_item_cell
//        override fun createDataBindingView(view: View): View? {
//            binding = AttractionsItemCellBinding.bind(view)
//            return view.rootView
//        }
//    }


}