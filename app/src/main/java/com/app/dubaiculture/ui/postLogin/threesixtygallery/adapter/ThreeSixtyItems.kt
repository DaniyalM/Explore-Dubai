package com.app.dubaiculture.ui.postLogin.threesixtygallery.adapter

import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.items_360_gallery_view.view.*

class ThreeSixtyItems (val img:Int): BaseAdapter(R.layout.items_360_gallery_view)  {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                it.img_rounded.setBackgroundResource(img)
            }
        }
    }
}