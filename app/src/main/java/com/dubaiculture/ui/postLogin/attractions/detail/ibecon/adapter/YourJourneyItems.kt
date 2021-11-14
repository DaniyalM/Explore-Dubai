package com.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.dubaiculture.databinding.ItemsYourJourneyBinding
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.glideInstance
import com.dubaiculture.utils.setTextColorRes
import com.xwray.groupie.databinding.BindableItem

class YourJourneyItems<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val beaconItems: BeaconItems,
    val resLayout: Int = R.layout.items_your_journey,
) : BindableItem<T>() {


    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsYourJourneyBinding -> {
                viewBinding.let {
                    if (beaconItems.isVisited == true) {
                        it.rootlayout.setOnClickListener {
                            rowClickListener!!.rowClickListener(position)
                        }
                    } else {
                        it.rootlayout.alpha = 0.5f
                        it.tickMark.visibility = View.GONE
                        it.tvCircle.setBackgroundResource(R.drawable.circle_path)
                        it.tvCircle.setTextColorRes(R.color.black_200)
                    }


                    it.tvCircle.text = beaconItems.step
                    it.title.text = beaconItems.title

                    it.imgMuseums.glideInstance(beaconItems.image, false)
                        .into(it.imgMuseums)
                }
            }
        }

    }

    override fun getLayout() = resLayout

}