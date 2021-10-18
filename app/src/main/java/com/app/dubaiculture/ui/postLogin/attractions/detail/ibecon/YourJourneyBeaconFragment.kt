package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.data.repository.sitemap.local.BeaconItems
import com.app.dubaiculture.databinding.FragmentYourJourneyBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.BeaconListAdapter
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.clicklisteners.BeaconClickListener
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.viewmodel.BeaconSharedViewModel
import com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.viewmodel.BeaconViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class YourJourneyBeaconFragment : BaseBottomSheetFragment<FragmentYourJourneyBinding>() {
    //    private val siteMapViewModel: SiteMapViewModel by viewModels()
    private val ibeaconViewModel: BeaconViewModel by viewModels()
    private val ibeaconShareViewModel: BeaconSharedViewModel by activityViewModels()

    private lateinit var beaconListAdapter: BeaconListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(ibeaconViewModel)
        rvBecons()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservable()
    }

    private fun subscribeToObservable() {
        ibeaconViewModel.beaconList.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                beaconListAdapter.submitList(it)
            }
        }
        ibeaconShareViewModel.isVisited.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it) {
                    ibeaconViewModel.refreshList()
                }
            }

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentYourJourneyBinding.inflate(inflater, container, false)


    private fun rvBecons() {
        binding.rvIbeacons.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            beaconListAdapter = BeaconListAdapter(object : BeaconClickListener {
                override fun onClick(beaconItems: BeaconItems) {

                    (parentFragment as NavHostFragment).navController.navigate(
                        YourJourneyBeaconFragmentDirections.actionYourJourneyBeaconFragmentToIbeconDescFragment(
                            beaconItems
                        )
                    )
                }
            })

            adapter = beaconListAdapter
        }

    }

}