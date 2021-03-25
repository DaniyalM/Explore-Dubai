package com.app.dubaiculture.ui.postLogin.attractions.adapters

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

data class AttractionListItem<T : ViewDataBinding>(
    private val favChecker: FavouriteChecker? = null,
    private val rowClickListener: RowClickListener? = null,
    val attraction: Attractions? = null,
    val resLayout: Int = R.layout.attraction_list_item_cell,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is AttractionListItemCellBinding -> {
                viewBinding.attractions = attraction
            }

        }
    }
}