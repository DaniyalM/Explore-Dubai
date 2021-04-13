package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.sitemap.local.IbeconITemsSiteMap
import com.app.dubaiculture.databinding.ItemsYourJourneyBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.glideInstance
import com.xwray.groupie.databinding.BindableItem
import kotlinx.android.synthetic.main.explore_map_layout_headers.view.*

class YourJourneyItems <T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val ibeconITemsSiteMap: IbeconITemsSiteMap,
    val resLayout: Int = R.layout.items_your_journey,
) : BindableItem<T>() {


    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsYourJourneyBinding -> {
                viewBinding.let {
                        it.rootlayout.setOnClickListener {
                            rowClickListener!!.rowClickListener(position)
                        }

                    it.tvCircle.text = ibeconITemsSiteMap.step
                    it.title.text = ibeconITemsSiteMap.title

                    it.imgMuseums.glideInstance(ibeconITemsSiteMap.image, false)
                        .into(it.imgMuseums)
                }
            }
        }
    }

    override fun getLayout() = resLayout

}