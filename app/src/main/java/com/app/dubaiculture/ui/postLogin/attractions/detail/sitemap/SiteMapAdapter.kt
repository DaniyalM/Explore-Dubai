package com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.xwray.groupie.databinding.BindableItem

class SiteMapAdapter<T : ViewDataBinding>(
    val siteMapModel: SiteMapModel,
    val resLayout: Int = R.layout.site_view_map_items,
) : BindableItem<T>() {


    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is SiteViewMapItemsBinding -> {

            }
        }
    }

    override fun getLayout() = resLayout

}