package com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentSiteMapBinding
import com.app.dubaiculture.databinding.SiteViewMapItemsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.sitemap.viewmodel.SiteMapViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SiteMapFragment : BaseFragment<FragmentSiteMapBinding>() ,View.OnClickListener {
    private  val siteMapViewModel : SiteMapViewModel by viewModels()
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
                    groupAdapter.add(SiteMapAdapter<SiteViewMapItemsBinding>(
                        siteMapModel = it,
                        resLayout = R.layout.site_view_map_items
                    ))
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