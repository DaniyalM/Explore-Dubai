package com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentSiteMapBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_ID
import com.app.dubaiculture.utils.glideInstance
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*
import javax.inject.Inject

@AndroidEntryPoint
class SiteMapFragment : BaseFragment<FragmentSiteMapBinding>() ,View.OnClickListener {
    private  val siteMapViewModel : SiteMapViewModel by viewModels()
    @Inject
    lateinit var glide: RequestManager
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSiteMapBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(siteMapViewModel)
        arguments.let {
            siteMapViewModel.siteMap(it?.getString(ATTRACTION_ID).toString(),getCurrentLanguage().language)
        }
        callingObserver()
        binding.imgClose.setOnClickListener(this)

    }

    private fun rvSetUp() {
        binding.rvSiteMap.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
        }
    }
    private fun callingObserver(){
        siteMapViewModel.siteMapData.observe(viewLifecycleOwner){
                it.let {
                    glide.load(BuildConfig.BASE_URL + it.ibeconImg)
                        .into(binding.siteMap)
                    it.ibeconItems.forEach {
                        groupAdapter.add(SiteMapAdapter<SiteViewMapItemsBinding>(
                            ibeconITemsSiteMap = it,
                            resLayout = R.layout.site_view_map_items
                        ))
                    }


                }
            rvSetUp()
        }
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.back ->{
                back()
            }
        }
    }
}