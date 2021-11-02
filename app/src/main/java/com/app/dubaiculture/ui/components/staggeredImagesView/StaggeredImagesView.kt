package com.app.dubaiculture.ui.components.staggeredImagesView

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.utils.hide
import com.app.dubaiculture.utils.show

import com.bumptech.glide.RequestManager
import java.io.File


class StaggeredImagesView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var img1: ImageView
    private var img2: ImageView
    private var img3: ImageView
    private var img4: ImageView
    private var img5: ImageView
    private var counterTextView: TextView
    private lateinit var images: MutableList<ImageView>
    private val view: View = inflate(context, R.layout.staggered_grid_view, null)

    init {
        img1 = view.findViewById(R.id.img1)
        img2 = view.findViewById(R.id.img2)
        img3 = view.findViewById(R.id.img3)
        img4 = view.findViewById(R.id.img4)
        img5 = view.findViewById(R.id.img5)
        counterTextView = view.findViewById(R.id.count)
        addView(view)

    }

    fun initialize(
        list: List<String>,
        clickListener: StaggeredImagesClickListener? = null,
        glide: RequestManager,
        isLocalLoad: Boolean = false
    ) {
        resetViews()
        list.size.let {
            if (it > 0) {
                initializeImagesList(it)
                setupHeightPercentage(it)
                setupImageVisibility(list, glide, isLocalLoad)
                setupRemainingImageCounter(it)
                setupClickListeners(clickListener)
            }
        }
    }

    private fun resetViews() {
        img1.hide()
        img2.hide()
        img3.hide()
        img4.hide()
        img5.hide()
        counterTextView.hide()
        counterTextView.text = ""

    }

    private fun setupClickListeners(clickListener: StaggeredImagesClickListener? = null) {
        clickListener?.let { listener ->
            img1.setOnClickListener { listener.onClick(0) }
            img2.setOnClickListener { listener.onClick(1) }
            img3.setOnClickListener { listener.onClick(1) }
            img4.setOnClickListener { listener.onClick(2) }
            img5.setOnClickListener { listener.onClick(3) }
            counterTextView.setOnClickListener { listener.onClick(3) }

        }
    }

    private fun setupRemainingImageCounter(size: Int) {
        if (size > 4) {
            counterTextView.show()
            counterTextView.text = "+${(size - 4)}"
        }
    }

    private fun initializeImagesList(size: Int) {
        images = if (size <= 2) {
            mutableListOf(img1, img2)
        } else {
            mutableListOf(img1, img3, img4, img5)
        }
    }

    private fun setupImageVisibility(
        list: List<String>,
        glide: RequestManager,
        localLoad: Boolean = false
    ) {
        var listSize = list.size
        if (listSize > 4)
            listSize = 4

        for (i in 0 until listSize) {
            images[i].show()
            if (localLoad) {
                if(Patterns.WEB_URL.matcher(list[i]).matches())
                {
                    glide.load(BuildConfig.BASE_URL +list[i]).fitCenter().into(images[i])
                }
                else{
                    glide.load(File(BuildConfig.BASE_URL +list[i])).into(images[i])
                }
            } else {
                glide.load(BuildConfig.BASE_URL +list[i]).fitCenter().into(images[i])
            }
        }
    }

    //changing height of img1 adjusts other images
    private fun setupHeightPercentage(size: Int) {
        val params = img1.layoutParams as ConstraintLayout.LayoutParams
        when {
            size == 3 -> {
                params.matchConstraintPercentHeight = 0.66f
            }
            size > 3 -> {
                params.matchConstraintPercentHeight = 0.75f
            }
            else -> {
                params.matchConstraintPercentHeight = 1f
            }
        }
        img1.requestLayout()

    }

    interface StaggeredImagesClickListener {
        fun onClick(pos: Int)
    }
}
//https://stackoverflow.com/questions/33283493/recyclerview-recycled-viewholder-image-view-wrong-size
