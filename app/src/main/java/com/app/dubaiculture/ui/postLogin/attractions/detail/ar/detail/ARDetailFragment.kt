package com.app.dubaiculture.ui.postLogin.attractions.detail.ar.detail

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.app.dubaiculture.BuildConfig
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
import com.bumptech.glide.RequestManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ARDetailFragment : BaseFragment<FragmentARDetailBinding>(), View.OnClickListener {

    private val arDetailViewModel: ARDetailViewModel by viewModels()
    private val imagesList = ArrayList<Images>()
    private var id : String?=null
    var arDetailListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    @Inject
    lateinit var glide: RequestManager
    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?,
    ) = FragmentARDetailBinding.inflate(inflater, container, false)

//    override fun getTheme() = R.style.FullScreenDialog;
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
//                            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                            WindowManager.LayoutParams.FLAG_FULLSCREEN
//                    )
//                }
//
//            }
//
//        }
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(arDetailViewModel)
        arguments?.let {
            id = it.getString(META_DATA_ID)
            arDetailViewModel.getMetaDataAr(
                    it.getString(META_DATA_ID)?:"",
                    getCurrentLanguage().language
            )
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            arDetailViewModel.getMetaDataAr(
                    id?:"",
                    getCurrentLanguage().language
            )
        }
        bgRTL(binding.s)
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

                rvSetUp()
                if (arDetailListAdapter.itemCount>0){
                    arDetailListAdapter.clear()
                }

                val circularProgressDrawable = CircularProgressDrawable(requireContext())
                circularProgressDrawable.strokeWidth = 5f
                circularProgressDrawable.centerRadius = 30f
                circularProgressDrawable.start()
                binding.tvTitle.text = it.title
                binding.desc.text = it.desc
                if(!it.images.isNullOrEmpty()){
                    binding.customTextView.visibility = View.VISIBLE
                    glide.load(BuildConfig.BASE_URL + it.images[0].image).placeholder(circularProgressDrawable).into(binding.imgDetailPic)
//                binding.imgDetailPic.glideInstance(it.images!![0].image, isSvg = false)
                it.images?.map {
                    imagesList.add(it)
                    arDetailListAdapter.add(
                            ViewGalleryItems<ViewGalleryItemsBinding>(
                                    object : RowClickListener {
                                        override fun rowClickListener(position: Int) {

                                            val bundle = Bundle()
                                            bundle.putParcelableArrayList(
                                                    IMAGES_LIST,
                                                    imageSelectedByPosition(imagesList, position) as java.util.ArrayList<out Parcelable>
                                            )
                                            navigate(
                                                    R.id.action_ARDetailFragment_to_attraction_gallery,
                                                    bundle
                                            )
                                        }

                                        override fun rowClickListener(position: Int, imageView: ImageView) {

                                        }
                                    }, images = it,
                                    resLayout = R.layout.view_gallery_items,
                                    glide = glide
                            )
                    )
                }
            }else{
                binding.customTextView.visibility = View.GONE
            }
            }
        }
    }

    private fun rvSetUp() {
        binding.rvViewGallery.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = arDetailListAdapter
        }

    }

    private fun imageSelectedByPosition(imagesList: ArrayList<Images>, position: Int): ArrayList<Images> {
        val list = ArrayList<Images>()
        list.add(imagesList[position])
        imagesList.forEachIndexed { index, elements ->
            if (position != index) {
                list.add(elements)
            }
        }
        return list
    }
}