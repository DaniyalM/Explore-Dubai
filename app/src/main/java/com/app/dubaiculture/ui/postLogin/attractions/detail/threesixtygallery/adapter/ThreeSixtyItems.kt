package com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.adapter

import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.items_360_gallery_view.view.*

class ThreeSixtyItems (val img:Int ,var invokeItem: invokeItem): BaseAdapter(R.layout.items_360_gallery_view)  {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                it.img_rounded.setOnClickListener {
                    invokeItem.onItemClick(img)
                }
                it.img_rounded.setBackgroundResource(img)
            }
        }
    }


}
interface  invokeItem{
    fun onItemClick(img:Int?=0)
}