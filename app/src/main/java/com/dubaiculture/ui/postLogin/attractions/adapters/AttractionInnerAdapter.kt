package com.dubaiculture.ui.postLogin.attractions.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.databinding.AttractionListItemCellBinding
import com.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionClickListener
import com.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker


class AttractionInnerAdapter(
    private val favChecker: FavouriteChecker? = null,
    private val attractionClickListener: AttractionClickListener? = null,
    private val isArabic: Boolean?=null,
) : PagingDataAdapter<Attractions, AttractionInnerAdapter.AttractionViewHolder>(object :
    DiffUtil.ItemCallback<Attractions>() {
    override fun areItemsTheSame(
        oldItem: Attractions,
        newItem: Attractions
    ) = oldItem.hashCode() == newItem.hashCode()


    override fun areContentsTheSame(
        oldItem: Attractions,
        newItem: Attractions
    ) = oldItem.id == newItem.id
}) {


    inner class AttractionViewHolder(
       val binding: AttractionListItemCellBinding,
       val favChecker: FavouriteChecker?,
       val attractionClickListener: AttractionClickListener?
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(attractions: Attractions){
            binding.attractions=attractions

            binding.apply {
                attractions.apply {

                    attractionImage.setOnClickListener {
                        attractionClickListener?.rowClickListener(this)
                    }
                    attractionImage.setOnClickListener {
                        attractionClickListener?.rowClickListener(
                            absoluteAdapterPosition,
                            imgMustSee,
                            this
                        )
                    }

                    if (IsFavourite) {
                        favourite.background =
                            ContextCompat.getDrawable(binding.root.context, R.drawable.heart_icon_fav)
                    }
                    favourite.setOnClickListener {
                        favChecker?.checkFavListener(
                            favourite,
                            absoluteAdapterPosition,
                            IsFavourite,
                            id
                        )
                    }

//                    if (!color.isNullOrEmpty()) {
//                        attractionImage.setCardBackgroundColor(Color.parseColor(getItem(position)?.color))
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                            attractionImage.outlineSpotShadowColor =
//                                Color.parseColor(color)
//                        }
//                    }
                }
            }

        }
    }






    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        return AttractionViewHolder(
            AttractionListItemCellBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            favChecker=favChecker,
            attractionClickListener=attractionClickListener
        )
    }


}