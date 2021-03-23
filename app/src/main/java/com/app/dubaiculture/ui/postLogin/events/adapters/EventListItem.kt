package com.app.dubaiculture.ui.postLogin.events.adapters

import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.ItemEventListingBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

data class EventListItem(
    private val favChecker : FavouriteChecker?=null, private val rowClickListener: RowClickListener?=null,
    val event: Events) : BindableItem<ItemEventListingBinding>() {
    override fun bind(viewBinding: ItemEventListingBinding, position: Int) {
        viewBinding.events = event
    }

    override fun getLayout() = R.layout.item_event_listing
}