package com.app.dubaiculture.ui.postLogin.attractions.detail.adapter

import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.databinding.AttractionDetailUpComingItemsBinding
import com.xwray.groupie.databinding.BindableItem

data class UpComingItems(
   val model: Events,
) : BindableItem<AttractionDetailUpComingItemsBinding>() {
    override fun bind(viewBinding: AttractionDetailUpComingItemsBinding, position: Int) {
        viewBinding.events = model
    }

    override fun getLayout() = R.layout.attraction_detail_up_coming_items
}
