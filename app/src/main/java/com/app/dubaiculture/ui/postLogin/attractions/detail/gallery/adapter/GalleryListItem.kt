package com.app.dubaiculture.ui.postLogin.attractions.detail.gallery.adapter

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.databinding.AttractionGallaryImageItemBinding
import com.app.dubaiculture.databinding.Items360GalleryViewBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.bumptech.glide.RequestManager
import com.xwray.groupie.databinding.BindableItem

class GalleryListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val attraction: Gallery,
    val resLayout: Int = R.layout.attraction_gallary_image_item,
    val glide: RequestManager? = null,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {

            is AttractionGallaryImageItemBinding -> {
                viewBinding.attraction = attraction
            }
            is Items360GalleryViewBinding -> {
                viewBinding.imgRounded.apply {
                    glide!!.load(BuildConfig.BASE_URL + attraction.galleryImage).into(this)

                    setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }

                }


            }


        }
    }
}