package com.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.adapter

import androidx.databinding.ViewDataBinding
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.ThreeSixtyImageItem
import com.dubaiculture.databinding.Items360GalleryViewBinding
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.bumptech.glide.RequestManager
import com.xwray.groupie.databinding.BindableItem

class ThreeSixtyListItem<T : ViewDataBinding>(
        private val rowClickListener: RowClickListener? = null,
        val imageItem: ThreeSixtyImageItem,
        val resLayout: Int = R.layout.items_360_gallery_view,
        val glide: RequestManager? = null
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {


        when (viewBinding) {
            is Items360GalleryViewBinding -> {
                viewBinding.imgRounded.setOnClickListener {
                    rowClickListener?.rowClickListener(position)
                }
                viewBinding.threesixty = imageItem
                glide?.let {
                    it.asBitmap().load(imageItem.image).thumbnail(0.1f).centerCrop().into(viewBinding.imgRounded)

                }
            }
        }
    }

    override fun getLayout() = resLayout

}