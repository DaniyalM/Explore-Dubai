package com.app.dubaiculture.ui.postLogin.attractions.detail.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Gallery
import com.app.dubaiculture.databinding.AttractionGallaryImageItemBinding
import com.app.dubaiculture.databinding.AttractionGalleryFragmentBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.gallery.adapter.GalleryListItem
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_GALLERY_LIST
import com.bumptech.glide.RequestManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AttractionGalleryFragment : BaseFragment<AttractionGalleryFragmentBinding>() {
    private lateinit var gallerList: ArrayList<Gallery>

    @Inject
    lateinit var glide: RequestManager

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
//                                glide.load(BuildConfig.BASE_URL+it.galleryImage).into(binding.mainImage)
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
}