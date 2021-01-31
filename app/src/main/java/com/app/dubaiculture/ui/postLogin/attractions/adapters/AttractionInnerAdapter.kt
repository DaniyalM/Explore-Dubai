package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.Attraction
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.attractions_item_cell.view.*

class AttractionInnerAdapter(val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Attraction>() {
        override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    var attractions: List<Attraction>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class AttractionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AttractionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.attractions_item_cell,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val attraction = attractions[position]
        holder.itemView.apply {
          //  glide.load(attraction.image_url).into(attraction_image)
//            glide.load(attraction.image).into(attraction_image)
            attraction_title_text.text = attraction.title
        }
    }

    override fun getItemCount() = attractions.size

//    private inner class AttractionInnerItemCell(context: Context) : AsyncCell(context) {
//        var binding: AttractionsItemCellBinding? = null
//        override val layoutId = R.layout.attractions_item_cell
//        override fun createDataBindingView(view: View): View? {
//            binding = AttractionsItemCellBinding.bind(view)
//            return view.rootView
//        }
//    }


}