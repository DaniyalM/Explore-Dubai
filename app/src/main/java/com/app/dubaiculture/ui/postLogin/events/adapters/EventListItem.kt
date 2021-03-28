package com.app.dubaiculture.ui.postLogin.events.adapters

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.AttractionDetailUpComingItemsBinding
import com.app.dubaiculture.databinding.EventItemsBinding
import com.app.dubaiculture.databinding.ItemEventListingBinding
import com.app.dubaiculture.databinding.UpcomingEventsInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.item_event_listing.view.*

data class EventListItem<T : ViewDataBinding>(
    private val favChecker: FavouriteChecker? = null,
    private val rowClickListener: RowClickListener? = null,
    val event: Events,
    val resLayout: Int = R.layout.item_event_listing,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is UpcomingEventsInnerItemCellBinding -> {
                viewBinding.let {
                    it.events = event
                    it. favourite.isChecked = event.isFavourite

                    it.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(it.favourite,
                                position,
                                event.isFavourite,
                                itemId)

                        }
                    }
                    it.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
            is ItemEventListingBinding -> {
                viewBinding.let {
                    it.events = event
                    it. favourite.isChecked = event.isFavourite

                    it.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(it.favourite,
                                position,
                                event.isFavourite,
                                itemId)

                        }
                    }
                    it.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
            is EventItemsBinding -> {
                viewBinding.let {
                    it.events = event
                    it. favourite.isChecked = event.isFavourite

                    it.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(it.favourite,
                                position,
                                event.isFavourite,
                                itemId)

                        }
                    }
                    it.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
            is AttractionDetailUpComingItemsBinding -> {
                viewBinding.let {
                    it.events = event
                    it. favourite.isChecked = event.isFavourite

                    it.favourite.setOnClickListener {
                        event.id?.let { itemId ->
                            favChecker!!.checkFavListener(it.favourite,
                                position,
                                event.isFavourite,
                                itemId)

                        }
                    }
                    it.cardview.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
        }


    }
}