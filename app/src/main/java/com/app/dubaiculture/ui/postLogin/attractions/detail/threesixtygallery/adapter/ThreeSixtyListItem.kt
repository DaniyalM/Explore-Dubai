package com.app.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.ThreeSixtyImageItem
import com.app.dubaiculture.databinding.Items360GalleryViewBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.vr.sdk.widgets.pano.VrPanoramaView
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
                glide?.asBitmap()?.load(imageItem.image)?.into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
            //                        panorama.loadImageFromBitmap(resource, VrPanoramaView.Options())
                     glide.load(resource).centerCrop().into(  viewBinding.imgRounded)
//                      .setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }

    override fun getLayout() = resLayout

}