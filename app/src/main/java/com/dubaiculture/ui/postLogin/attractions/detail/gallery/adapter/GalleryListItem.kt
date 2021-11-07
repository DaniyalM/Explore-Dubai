package com.dubaiculture.ui.postLogin.attractions.detail.gallery.adapter

import androidx.databinding.ViewDataBinding
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.Gallery
import com.dubaiculture.data.repository.viewgallery.local.Images
import com.dubaiculture.databinding.AttractionGallaryImageItemBinding
import com.dubaiculture.databinding.Items360GalleryViewBinding
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.glideInstance
import com.bumptech.glide.RequestManager
import com.xwray.groupie.databinding.BindableItem

class GalleryListItem<T : ViewDataBinding>(
    private val rowClickListener: RowClickListener? = null,
    val attraction: Gallery?=null,
    val images : Images?=null,
    val resLayout: Int = R.layout.attraction_gallary_image_item,
    val glide: RequestManager? = null,
) : BindableItem<T>() {
    override fun getLayout() = resLayout
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is AttractionGallaryImageItemBinding -> {
                val circularProgressDrawable = CircularProgressDrawable(viewBinding.root.context)
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                if(!attraction?.galleryImage.isNullOrEmpty()){

                    viewBinding.attraction = attraction
//                    viewBinding.mainImage.glideInstance(attraction?.galleryImage, false ).into(viewBinding.mainImage)
                    glide!!.load(BuildConfig.BASE_URL + attraction?.galleryImage).placeholder(circularProgressDrawable).into(viewBinding.mainImage)
                }else{

                    glide!!.load(BuildConfig.BASE_URL + images?.image).placeholder(circularProgressDrawable).into(viewBinding.mainImage)
                }
            }
            is Items360GalleryViewBinding -> {
                viewBinding.imgRounded.apply {
                    val circularProgressDrawable = CircularProgressDrawable(viewBinding.root.context)
                    circularProgressDrawable.strokeWidth = 5f
                    circularProgressDrawable.centerRadius = 30f
                    circularProgressDrawable.start()
                    if(!attraction?.galleryImage.isNullOrEmpty()){
                        glide!!.load(BuildConfig.BASE_URL + attraction?.galleryThumbnail).placeholder(circularProgressDrawable).into(this)
                    }else{
                        glide!!.load(BuildConfig.BASE_URL + images?.image).placeholder(circularProgressDrawable).into(this)
                    }
                    setOnClickListener {
                        rowClickListener!!.rowClickListener(position)
                    }
                }
            }
        }
    }
}