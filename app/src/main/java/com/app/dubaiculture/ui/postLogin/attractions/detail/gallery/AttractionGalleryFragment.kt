package com.app.dubaiculture.ui.postLogin.attractions.detail.gallery

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.databinding.AttractionGallaryImageItemBinding
import com.app.dubaiculture.databinding.AttractionGalleryFragmentBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.gallery.adapter.GalleryListItem
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_GALLERY_LIST
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.blurry.Blurry
import javax.inject.Inject

@AndroidEntryPoint
class AttractionGalleryFragment : BaseDialogFragment<AttractionGalleryFragmentBinding>() {
    private lateinit var gallerList: ArrayList<Gallery>

    @Inject
    lateinit var glide: RequestManager

    override fun getTheme(): Int {
        return R.style.FullScreenDialog;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)

    }

    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog?.window!!.apply {
                setLayout(width, height)
                @Suppress("DEPRECATION")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.hide(WindowInsets.Type.statusBars())
                } else {
                    setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                    )
                }

            }

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = AttractionGalleryFragmentBinding.inflate(inflater, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            gallerList = getParcelableArrayList(ATTRACTION_GALLERY_LIST)!!
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRv()

        gallerList[0].galleryImage?.let { displayBlurryView(it) }
        binding.imgBack.setOnClickListener {
            back()
        }
    }


    private fun initRv() {
        binding.mainImageSlider.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                gallerList.forEach {
                    add(GalleryListItem<AttractionGallaryImageItemBinding>(attraction = it,
                        glide=glide))
                }
            }

            LinearSnapHelper().attachToRecyclerView(this)

        }
        binding.rvBottomSelector.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GroupAdapter<GroupieViewHolder>().apply {
                gallerList.forEach {
                    add(GalleryListItem<AttractionGallaryImageItemBinding>(
                        rowClickListener = object : RowClickListener {
                            override fun rowClickListener(position: Int) {

                                it.galleryImage?.let {
                                    displayBlurryView(it)
                                }



                                binding.mainImageSlider.smoothScrollToPosition(position)
                            }
                        },
                        attraction = it,
                        resLayout = R.layout.items_360_gallery_view,
                        glide=glide
                    ))
                }
            }
            LinearSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun displayBlurryView(it:String){
        glide.asBitmap().load(BuildConfig.BASE_URL+it)
            .into(object : CustomTarget<Bitmap>(){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?,
                ) {
                    Blurry.with(activity).from(resource).into(binding.mainImage)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })

    }
}