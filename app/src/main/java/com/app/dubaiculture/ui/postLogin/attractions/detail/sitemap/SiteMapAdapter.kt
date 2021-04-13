package com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.data.repository.sitemap.local.SiteMapModel
import com.app.dubaiculture.databinding.ItemsYourJourneyBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.xwray.groupie.databinding.BindableItem

class SiteMapAdapter<T : ViewDataBinding>(
    val ibeconITemsSiteMap: IbeconITemsSiteMap,
    val resLayout: Int = R.layout.site_view_map_items,
) : BindableItem<T>() {


    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is SiteViewMapItemsBinding -> {
                viewBinding.let {
                    it.tvNumber.text = ibeconITemsSiteMap.step
                    it.tvTitle.text = ibeconITemsSiteMap.title

                }
            }
        }
    }

    override fun getLayout() = resLayout

}