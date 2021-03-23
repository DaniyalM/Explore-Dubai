package com.app.dubaiculture.ui.postLogin.attractions.adapters

import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.xwray.groupie.databinding.BindableItem

data class AttractionListItem(
    val attraction: Attractions,
) : BindableItem<AttractionListItemCellBinding>() {
    override fun bind(viewBinding: AttractionListItemCellBinding, position: Int) {
        viewBinding.attractions = attraction
    }

    override fun getLayout() = R.layout.attraction_list_item_cell
}