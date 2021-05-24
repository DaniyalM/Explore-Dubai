package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.explore.local.models.LatestNews
import com.app.dubaiculture.data.repository.explore.local.models.PopularServices
import com.app.dubaiculture.databinding.LatestNewsInnerItemCellBinding
import com.app.dubaiculture.databinding.PopularServiceInnerItemCellBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

class PopularServiceListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val services: PopularServices? = null,
    val resLayout: Int = R.layout.popular_service_inner_item_cell,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is PopularServiceInnerItemCellBinding -> {
                viewBinding.popularServices = services
            }

        }
    }
}
