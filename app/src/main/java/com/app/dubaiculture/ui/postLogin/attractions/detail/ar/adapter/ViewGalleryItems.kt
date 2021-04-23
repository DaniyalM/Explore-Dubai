package com.app.dubaiculture.ui.postLogin.attractions.detail.ar.adapter

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.viewgallery.local.Images
import com.app.dubaiculture.databinding.ViewGalleryItemsBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.glideInstance
import com.bumptech.glide.RequestManager
import com.xwray.groupie.databinding.BindableItem

class ViewGalleryItems<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val images: Images,
    val resLayout: Int = R.layout.view_gallery_items,
    val glide: RequestManager? = null,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ViewGalleryItemsBinding -> {
                viewBinding.let {
                    it.imgRounded.glideInstance(images.image, isSvg = false).into(it.imgRounded)
                    it.card.setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
        }
    }
}