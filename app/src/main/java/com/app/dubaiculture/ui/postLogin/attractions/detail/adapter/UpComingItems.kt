package com.app.dubaiculture.ui.postLogin.attractions.detail.adapter

import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.AttractionDetailUpComingItemsBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

data class UpComingItems(
    private val favChecker : FavouriteChecker?=null, private val rowClickListener: RowClickListener?=null,
   val event: Events,
) : BindableItem<AttractionDetailUpComingItemsBinding>() {
    override fun bind(viewBinding: AttractionDetailUpComingItemsBinding, position: Int) {
        viewBinding.events = event
    }

    override fun getLayout() = R.layout.attraction_detail_up_coming_items
}
