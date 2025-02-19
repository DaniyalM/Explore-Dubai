package com.dubaiculture.ui.postLogin.attractions.detail.gallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.attraction.local.models.Gallery
import com.dubaiculture.data.repository.viewgallery.local.Images
import com.dubaiculture.databinding.AttractionGallaryImageItemBinding
import com.dubaiculture.databinding.AttractionGalleryFragmentBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.attractions.detail.gallery.adapter.GalleryListItem
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.Constants.NavBundles.ATTRACTION_GALLERY_LIST
import com.dubaiculture.utils.Constants.NavBundles.IMAGES_LIST
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.blurry.Blurry
import javax.inject.Inject


@AndroidEntryPoint
class AttractionGalleryFragment : BaseFragment<AttractionGalleryFragmentBinding>() {
    private var gallerList = ArrayList<Gallery>()
    private var imagesList = ArrayList<Images>()

    @Inject
    lateinit var glide: RequestManager

//    override fun getTheme(): Int {
//        return R.style.FullScreenDialog;
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)
//
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (dialog != null) {
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog?.window!!.apply {
//                setLayout(width, height)
//                @Suppress("DEPRECATION")
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    insetsController?.hide(WindowInsets.Type.statusBars())
//                } else {
//                    setFlags(
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN
//                    )
//                }
//
//            }
//
//        }
//    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = AttractionGalleryFragmentBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            try {
                gallerList = getParcelableArrayList(ATTRACTION_GALLERY_LIST)!!
            } catch (e: NullPointerException) {
                imagesList = getParcelableArrayList(IMAGES_LIST)!!
            }

        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        initRv()
        if (!gallerList.isNullOrEmpty()) {
            gallerList[0].galleryImage?.let { displayBlurryView(it) }
        } else {
            if (imagesList.isNotEmpty()){
                imagesList[0].image?.let { displayBlurryView(it) }
            }

        }
        binding.imgBack.setOnClickListener {
            back()
        }
    }


    private fun initRv() {
        binding.mainImageSlider.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                if (!gallerList.isNullOrEmpty()) {
                    gallerList.forEach {
                        add(
                            GalleryListItem<AttractionGallaryImageItemBinding>(
                                attraction = it,
                                glide = glide
                            )
                        )
                    }
                } else {
                    imagesList.forEach {
                        add(
                            GalleryListItem<AttractionGallaryImageItemBinding>(
                                images = it,
                                glide = glide
                            )
                        )
                    }
                }
            }

//            addOnItemTouchListener(object : SimpleOnItemTouchListener() {
//                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//                    // Stop only scrolling.
//                    return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
//                }
//            })
            LinearSnapHelper().attachToRecyclerView(this)
        }
        binding.rvBottomSelector.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                if (!gallerList.isNullOrEmpty()) {
                    gallerList.forEach {
                        add(
                            GalleryListItem<AttractionGallaryImageItemBinding>(
                                rowClickListener = object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        it.galleryImage?.let {
                                            displayBlurryView(it)
                                        }
                                        binding.mainImageSlider.smoothScrollToPosition(position)
                                    }

                                    override fun rowClickListener(position: Int, imageView: ImageView) {
                                    }
                                },
                                attraction = it,
                                resLayout = R.layout.items_360_gallery_view,
                                glide = glide
                            )
                        )
                    }

                } else {
                    imagesList.forEach {
                        add(
                            GalleryListItem<AttractionGallaryImageItemBinding>(
                                rowClickListener = object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        it.image?.let {
                                            displayBlurryView(it)
                                        }
                                        binding.mainImageSlider.smoothScrollToPosition(position)
                                    }

                                    override fun rowClickListener(position: Int, imageView: ImageView) {

                                    }
                                },
                                images = it,
                                resLayout = R.layout.items_360_gallery_view,
                                glide = glide
                            )
                        )
                    }
                }
            }
        }
    }

    private fun displayBlurryView(it: String) {
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        glide.asBitmap().load(BuildConfig.BASE_URL + it)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?,
                ) {
                    circularProgressDrawable.stop()
                    Blurry.with(activity).radius(10)
                        .sampling(8)
                        .color(ContextCompat.getColor(activity, R.color.white_transparent))
                        .async()
                        .animate(500).from(resource).into(binding.mainImage)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    circularProgressDrawable.stop()

                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }
            })

    }
}