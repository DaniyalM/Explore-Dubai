package com.app.dubaiculture.ui.postLogin.attractions.detail.ar.detail

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.viewgallery.local.Images
import com.app.dubaiculture.databinding.FragmentARDetailBinding
import com.app.dubaiculture.databinding.ViewGalleryItemsBinding
import com.app.dubaiculture.ui.base.BaseDialogFragment
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.ar.adapter.ViewGalleryItems
import com.app.dubaiculture.ui.postLogin.attractions.detail.ar.viewModel.ARDetailViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants.NavBundles.IMAGES_LIST
import com.app.dubaiculture.utils.Constants.NavBundles.META_DATA_ID
import com.app.dubaiculture.utils.glideInstance
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ARDetailFragment : BaseDialogFragment<FragmentARDetailBinding>(), View.OnClickListener {

    private val arDetailViewModel: ARDetailViewModel by viewModels()
    private val imagesList = ArrayList<Images>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentARDetailBinding.inflate(inflater, container, false)

    override fun getTheme() = R.style.FullScreenDialog;

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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(arDetailViewModel)
        arguments?.let {
            arDetailViewModel.getMetaDataAr(
                it.getString(META_DATA_ID)!!,
                getCurrentLanguage().language
            )
        }

        binding.imgBack.setOnClickListener(this)
        callingObserver()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_back -> {
                back()
            }
        }
    }

    private fun callingObserver() {
        arDetailViewModel.metaDataAr.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvTitle.text = it.title
                binding.desc.text = it.desc
                binding.imgDetailPic.glideInstance(it.images!![0].image, isSvg = false)
                    .into(binding.imgDetailPic)
                rvSetUp()
                it.images.map {
                    imagesList.add(it)
                    groupAdapter.add(
                        ViewGalleryItems<ViewGalleryItemsBinding>(
                            object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    val bundle = Bundle()
                                    bundle.putParcelableArrayList(
                                        IMAGES_LIST,
                                        imagesList as java.util.ArrayList<out Parcelable>
                                    )
                                    navigate(
                                        R.id.action_ARDetailFragment_to_attraction_gallery,
                                        bundle
                                    )
                                }
                            }, images = it,
                            resLayout = R.layout.view_gallery_items
                        )
                    )
                }
            }
        }
    }

    private fun rvSetUp() {
        binding.rvViewGallery.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
        }

    }
}