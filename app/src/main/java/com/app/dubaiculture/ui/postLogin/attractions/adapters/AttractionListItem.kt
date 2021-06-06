package com.app.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.MustSeeInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.DatePickerHelper
import com.app.dubaiculture.utils.dateFormat
import com.app.dubaiculture.utils.dateFormatVisitedPlace
import com.app.dubaiculture.utils.toString
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.item_event_listing.view.*
import timber.log.Timber
import java.util.*

data class AttractionListItem<T : ViewDataBinding>(
        private val favChecker: FavouriteChecker? = null,
        private val rowClickListener: RowClickListener? = null,
        val attraction: Attractions,
        val resLayout: Int = R.layout.attraction_list_item_cell,
        val context: Context,
        val isVisited: Boolean = false
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
//                    cardViewTitle.setCardBackgroundColor(Color.parseColor(attraction.color))
//                    category.setBackgroundColor(Color.parseColor(attraction.color))

                    if (attraction.IsFavourite) {
                        favourite.background = ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }
                    favourite.setOnClickListener {
                        favChecker?.checkFavListener(it.favourite,
                                position,
                                attraction.IsFavourite,
                                attraction.id)
                    }

                    if (isVisited){
                        visitedDateContainer.visibility=View.VISIBLE
                        visitedDateTime.text = "${context.resources.getString(R.string.visited_on)} ${dateFormatVisitedPlace(attraction.visitedDateTime,"dd MMM,yyyy")}"

                    }
                }
            }
            is MustSeeInnerItemCellBinding -> {
                viewBinding.attractions = attraction
                viewBinding.apply {
                    attractionImage.setOnClickListener {
                        rowClickListener?.rowClickListener(position)
                    }
                    if (attraction.IsFavourite) {
                        favourite.background = ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }
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