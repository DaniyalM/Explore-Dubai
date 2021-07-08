package com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.adapter

import android.widget.ImageView
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.recyclerstuf.BaseAdapter
import com.xwray.groupie.GroupieViewHolder

class ThreeSixtyItems (val img:Int ,var invokeItem: invokeItem): BaseAdapter(R.layout.items_360_gallery_view)  {
    override fun initBinding(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root?.let {
                it.findViewById<ImageView>(R.id.img_rounded).setOnClickListener {
                    invokeItem.onItemClick(img)
                }
                it.findViewById<ImageView>(R.id.img_rounded).setBackgroundResource(img)
            }
        }
    }


}
interface  invokeItem{
    fun onItemClick(img:Int?=0)
}