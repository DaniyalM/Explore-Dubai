package com.dubaiculture.ui.postLogin.attractions.detail.threesixtygallery.components

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.view.get
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.Assets360
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.vr.sdk.widgets.pano.VrPanoramaView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class VrPanoramaViewComponent(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    @Inject
    lateinit var glide: RequestManager
    private lateinit var panorama: VrPanoramaView
    lateinit var view: View
    lateinit var relativeLayout: RelativeLayout

    fun providePanorama(context: Activity) {
        view = inflate(context, R.layout.panorama_view_container, null)
        relativeLayout = view.findViewById(R.id.containerView)
        panorama = relativeLayout.findViewById(R.id.mVrPanoramaView)

        val baseFrameLayout = panorama[0] as ViewGroup

        // For "Exit fullscreen" button and "About VR View" button
        val relativeLayout = baseFrameLayout.getChildAt(1) as ViewGroup
        val exitFullscreenImageButton = relativeLayout.getChildAt(0)
        val aboutVRViewImageButton = relativeLayout.getChildAt(1)

        // For "Cardboard" button and "Enter fullscreen" button
        val linearLayout = relativeLayout.getChildAt(2) as ViewGroup
        val cardboardImageButton = linearLayout.getChildAt(0)
        val enterFullscreenImageButton = linearLayout.getChildAt(1)


        exitFullscreenImageButton.visibility = View.GONE
        aboutVRViewImageButton.visibility = View.GONE
        cardboardImageButton.visibility = View.GONE
        enterFullscreenImageButton.visibility = View.GONE

        addView(view)
    }


    fun initialize(image: String) {

        glide.apply {
            asBitmap().load(image).into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    panorama.loadImageFromBitmap(resource, VrPanoramaView.Options())
         }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
        }

    }
}