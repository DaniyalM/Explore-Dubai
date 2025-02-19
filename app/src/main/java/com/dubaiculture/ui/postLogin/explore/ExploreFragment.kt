package com.dubaiculture.ui.postLogin.explore

import android.Manifest
import android.location.Location
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.databinding.FragmentExploreBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.dubaiculture.ui.postLogin.explore.viewmodel.ExploreViewModel
import com.dubaiculture.utils.Constants.NavBundles.LOCATION_LAT
import com.dubaiculture.utils.Constants.NavBundles.LOCATION_LNG
import com.dubaiculture.utils.enableLocationFromSettings
import com.dubaiculture.utils.handleApiError
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.location.LocationHelper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
//import com.google.firebase.iid.FirebaseInstanceId
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {
    @Inject
    lateinit var locationHelper: LocationHelper
    private val exploreViewModel: ExploreViewModel by viewModels()
    private var exploreAdapter: ExploreRecyclerAsyncAdapter? = null
    private var lastFirstVisiblePosition: Int = 0


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreBinding.inflate(inflater, container, false)


    private fun getRecyclerView() = binding.rvExplore
    override fun onPause() {
        super.onPause()
        lastFirstVisiblePosition =
            (getRecyclerView().layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

    }

    override fun onResume() {
        super.onResume()
        try {

            getRecyclerView().smoothScrollToPosition(lastFirstVisiblePosition)

        } catch (ex: IllegalArgumentException) {
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        exploreViewModel.getExploreToScreen(getCurrentLanguage().language)


        locationHelper.provideContext(activity)
//        Timber.e("token explore " + FirebaseInstanceId.getInstance().token)

        subscribeUiEvents(exploreViewModel)
        binding.swipeRefresh.apply {

            setUpRecyclerView()
            subscribeToObservable()
            setOnRefreshListener {

                isRefreshing = false
                exploreViewModel.getExploreToScreen(getCurrentLanguage().language)


            }
            setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
            )
        }
        binding.toolbarSnippet.toolbarLayout.imgDrawer.setOnClickListener {
            locationPermission()
        }
//        binding.toolbarSnippet.toolbarLayout.search.hide()
        binding.toolbarSnippet.toolbarLayout.search.setOnClickListener {
            navigateByDirections(
                ExploreFragmentDirections.actionExploreFragmentToSearchNavigation()
            )
        }
//        if (!application.auth.isGuest) {
//
//        }


    }

    private fun setUpRecyclerView() {
        if (exploreAdapter == null) {
            exploreAdapter = ExploreRecyclerAsyncAdapter(
                isGuest = application.auth.isGuest,
                activity,
                fragment = this,
                baseViewModel = exploreViewModel
            )
            binding.rvExplore.apply {
                visibility = View.VISIBLE
                layoutManager = LinearLayoutManager(activity)
                adapter = exploreAdapter
//            this.itemAnimator = SlideInLeftAnimator()
            }
        }


    }

    private fun subscribeToObservable() {
        exploreViewModel.isFavourite.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            when (it) {
                is Result.Success -> {

                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_home)
                    }
                }
                is Result.Failure -> handleApiError(it, exploreViewModel)
            }
        }
        exploreViewModel.exploreList.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            when (it) {
                is Result.Success -> {
                    it.let {
                        exploreAdapter?.items = it.value
                    }
                }
                is Result.Failure -> handleApiError(it, exploreViewModel)
            }
        }
    }

    private fun locationPermission(destination: Int = R.id.action_exploreFragment_to_exploreMapFragment) {
        val quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = false
        )
        activity.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            options = quickPermissionsOption
        ) {

            if (!locationHelper.isLocationEnabled()) {
                exploreViewModel.showToast(message = resources.getString(R.string.please_enable_gps))
                if (!application.auth.isGuest) {
                    navigateByDirections(ExploreFragmentDirections.actionExploreFragmentToSettingsNavigation())
                } else {
                    activity.enableLocationFromSettings()
                }

            }
            locationHelper.locationSetUp(
                object : LocationHelper.LocationLatLng {
                    override fun getCurrentLocation(location: Location) {
                        exploreViewModel.showLoader(false)
                        val bundle = bundleOf(
                            LOCATION_LAT to location.latitude,
                            LOCATION_LNG to location.longitude
                        )

                        navigate(destination, bundle)
                    }
                },
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
//                        Timber.e("LocationCallback ${locationResult.lastLocation.latitude}")
                    }
                }
            )

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exploreAdapter = null
    }


}
