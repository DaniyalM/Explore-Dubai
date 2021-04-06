package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.MustSeeInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.item_event_listing.view.*

data class AttractionListItem<T : ViewDataBinding>(
    private val favChecker: FavouriteChecker? = null,
    private val rowClickListener: RowClickListener? = null,
    val attraction: Attractions,
    val resLayout: Int = R.layout.attraction_list_item_cell,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {

            is AttractionListItemCellBinding -> {
                viewBinding.attractions = attraction
                viewBinding.apply {
                    attractionImage.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                    }
                    cardViewTitle.setCardBackgroundColor(Color.parseColor(attraction.color))
//                    category.setBackgroundColor(Color.parseColor(attraction.color))
                    favourite.isChecked = attraction.IsFavourite

                    favourite.setOnClickListener {
                        favChecker?.checkFavListener(it.favourite,
                            position,
                            attraction.IsFavourite,
                            attraction.id)
                    }

                }

            }
            is MustSeeInnerItemCellBinding -> {
                viewBinding.attractions = attraction
                viewBinding.apply {
                    attractionImage.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                    }
                    favourite.isChecked = attraction.IsFavourite
                    favourite.setOnClickListener {
                        favChecker?.checkFavListener(it.favourite,
                            position,
                            attraction.IsFavourite,
                            attraction.id)
                    }

                }
            }

        }
    }
}