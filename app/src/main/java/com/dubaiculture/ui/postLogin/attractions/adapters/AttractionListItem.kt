package com.dubaiculture.ui.postLogin.attractions.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.databinding.AttractionListItemCellBinding
import com.dubaiculture.databinding.MustSeeInnerItemCellBinding
import com.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.dateFormatVisitedPlace
import com.xwray.groupie.databinding.BindableItem

//val attraction: Attractions,
data class AttractionListItem<T : ViewDataBinding>(
        private val favChecker: FavouriteChecker? = null,
        private val rowClickListener: RowClickListener? = null,
        val attraction: Attractions,
        val resLayout: Int = R.layout.attraction_list_item_cell,
        val context: Context,
        val isVisited: Boolean = false
) : BindableItem<T>() {
    private var lastPosition = -1

    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is AttractionListItemCellBinding -> {
                viewBinding.attractions = attraction
                viewBinding.apply {
                    attractionImage.setOnClickListener {
//                        rowClickListener?.rowClickListener(position)
                        rowClickListener?.rowClickListener(position)
                    }
//                    cardViewTitle.setCardBackgroundColor(Color.parseColor(attraction.color))
//                    category.setBackgroundColor(Color.parseColor(attraction.color))

                    if (attraction.IsFavourite) {
                        favourite.background = ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }
                    favourite.setOnClickListener {
                        favChecker?.checkFavListener(favourite,
                                position,
                                attraction.IsFavourite,
                                attraction.id)
                    }

                    if (isVisited) {
                        visitedDateContainer.visibility = View.VISIBLE
                        visitedDateTime.text = "${context.resources.getString(R.string.visited_on)} ${dateFormatVisitedPlace(attraction.visitedDateTime, "dd MMM,yyyy")}"

                    }
                }
            }
            is MustSeeInnerItemCellBinding -> {
                viewBinding.attractions = attraction

                viewBinding.apply {
                    attractionImage.setOnClickListener {
//                        rowClickListener?.rowClickListener(position)
                        rowClickListener?.rowClickListener(position, viewBinding.imgMustSee)
                    }
                    if (attraction.color!!.isNotEmpty())
                        cardViewTitle.setCardBackgroundColor(Color.parseColor(attraction.color))


                    if (attraction.IsFavourite) {
                        favourite.background =
                            ContextCompat.getDrawable(context, R.drawable.heart_icon_fav)
                    }
                    favourite.setOnClickListener {
                        favChecker?.checkFavListener(
                            favourite,
                            position,
                            attraction.IsFavourite,
                            attraction.id
                        )
                    }
                }
            }
        }
    }

//    override fun onViewAttachedToWindow(viewHolder: GroupieViewHolder<T>) {
//        super.onViewAttachedToWindow(viewHolder)
//    }
//
//    private fun setAnimation(viewToAnimate: View, position: Int) {
//        // If the bound view wasn't previously displayed on screen, it's animated
//        if (position > lastPosition) {
//            val animation: Animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
//            viewToAnimate.startAnimation(animation)
//            lastPosition = position
//        }
//    }
}