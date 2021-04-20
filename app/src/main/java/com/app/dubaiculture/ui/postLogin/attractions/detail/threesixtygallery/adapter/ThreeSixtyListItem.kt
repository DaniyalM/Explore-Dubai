package com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.adapter

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.ThreeSixtyImageItem
import com.app.dubaiculture.databinding.Items360GalleryViewBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

class ThreeSixtyListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val imageItem: ThreeSixtyImageItem,
    val resLayout: Int = R.layout.items_360_gallery_view,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is Items360GalleryViewBinding-> {
                viewBinding.imgRounded.setOnClickListener {
                    rowClickListener?.rowClickListener(position)
                }
                viewBinding.threesixty=imageItem
            }
        }
    }

    override fun getLayout() = resLayout

}