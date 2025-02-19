package com.dubaiculture.ui.postLogin.attractions.detail.sitemap

import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.SiteMaps
import com.dubaiculture.databinding.SiteViewMapItemsBinding
import com.xwray.groupie.databinding.BindableItem

class SiteMapAdapter<T : ViewDataBinding>(
    val siteMaps: SiteMaps,
    val resLayout: Int = R.layout.site_view_map_items,
) : BindableItem<T>() {


    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is SiteViewMapItemsBinding -> {
                viewBinding.let {
                    it.tvNumber.text = siteMaps.step
                    it.tvTitle.text = siteMaps.title

                }
            }
        }
    }

    override fun getLayout() = resLayout

}