package com.app.dubaiculture.ui.postLogin.nearyou.adapter

import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.items_museums_near_to_you.view.*


class NearToYouItems(
    private val imgMuseum: Int,
    private val museumTitle: String,
    private val museumLocation: String,
    private val km: String,
) : BaseAdapter(
    R.layout.items_museums_near_to_you) {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                it.img_museums.setBackgroundResource(imgMuseum)
                it.tv_place.text = museumTitle
                it.tv_location.text = museumLocation
                it.tv_km.text = km
            }
        }

    }
}