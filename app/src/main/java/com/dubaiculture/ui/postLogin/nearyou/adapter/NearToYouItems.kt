package com.dubaiculture.ui.postLogin.nearyou.adapter

import android.media.Image
import android.widget.ImageView
import android.widget.TextView
import com.dubaiculture.R
import com.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder


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
                it.findViewById<ImageView>(R.id.img_museums).setBackgroundResource(imgMuseum)
                it.findViewById<TextView>(R.id.tv_place).text = museumTitle
                it.findViewById<TextView>(R.id.tv_location).text = museumLocation
                it.findViewById<TextView>(R.id.tv_km).text = km
            }
        }

    }
}