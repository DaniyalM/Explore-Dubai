package com.app.dubaiculture.ui.postLogin.explore

import android.Manifest
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.databinding.FragmentExploreBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.explore.adapters.ExploreRecyclerAsyncAdapter
import com.app.dubaiculture.ui.postLogin.explore.viewmodel.ExploreViewModel
import com.app.dubaiculture.utils.Constants.NavBundles.LOCATION_LAT
import com.app.dubaiculture.utils.Constants.NavBundles.LOCATION_LNG
import com.app.dubaiculture.utils.handleApiError
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.RequestManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>() {
    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    @Inject
    lateinit var locationRequest: LocationRequest

    @Inject
    lateinit var locationHelper: LocationHelper

    @Inject
    lateinit var locationManager: LocationManager
    private val exploreViewModel: ExploreViewModel by viewModels()
    private lateinit var exploreAdapter: ExploreRecyclerAsyncAdapter
    private lateinit var explore: List<Explore>
    private var lastFirstVisiblePosition: Int = 0

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            exploreViewModel.showLoader(false)
            Timber.e("onLocationResult ${locationResult.lastLocation.latitude}")
        }
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentExploreBinding.inflate(inflater, container, false)


//    fun getRecyclerView() = binding.rvExplore

//    override fun onPause() {
//        super.onPause()
//        lastFirstVisiblePosition =
//            (getRecyclerView().layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                if (!this::exploreAdapter.isInitialized) {
        binding.swipeRefresh.post(object : Runnable {
            override fun run() {
                binding.swipeRefresh.isRefreshing = true
                callingObservables()

            }

        })
        setUpRecyclerView()
        }
        subscribeUiEvents(exploreViewModel)
        subscribeToObservable()
        applicationExitDialog()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            exploreViewModel.getExploreToScreen(getCurrentLanguage().language)

        }

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)





        binding.root.img_drawer.setOnClickListener {
            locationPermission()
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    showAlert(
                        message = getString(R.string.error_msg),
                        textPositive = getString(R.string.okay),
                        textNegative = getString(R.string.cancel),
                        actionNegative = {

                        },
                        actionPositive = {
                            activity.finish()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//    }

    private fun setUpRecyclerView() {
        exploreAdapter = ExploreRecyclerAsyncAdapter(activity,
            fragment = this,
            baseViewModel = exploreViewModel)
//        explore.items = createTestItems()
        binding.rvExplore.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(activity)
            adapter = exploreAdapter
            this.itemAnimator = SlideInLeftAnimator()
            (layoutManager as LinearLayoutManager).scrollToPosition(lastFirstVisiblePosition)

//            LinearSnapHelper().attachToRecyclerView(this)

        }

    }


    private fun callingObservables() {
        if (!this::explore.isInitialized) {

            lifecycleScope.launch {
                exploreViewModel.getExploreToScreen(getCurrentLanguage().language)
            }
        }

    }

    private fun subscribeToObservable() {

        exploreViewModel.isFavourite.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            when (it) {
                is Result.Success -> {

                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_home)
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
                        exploreAdapter.items=it.value
                    }
                }
                is Result.Failure -> handleApiError(it, exploreViewModel)
            }
        }
    }

    private fun applicationExitDialog() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    showAlert(
                        message = getString(R.string.error_msg),
                        textPositive = getString(R.string.okay),
                        textNegative = getString(R.string.cancel),
                        actionNegative = {

                        },
                        actionPositive = {
                            activity.finish()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun locationPermission() {
        val quickPermissionsOption = QuickPermissionsOptions(
            handleRationale = false
        )
        activity.runWithPermissions(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            options = quickPermissionsOption
        ) {
            exploreViewModel.showLoader(true)
            if (!locationHelper.isLocationEnabled(locationManager = locationManager)){
                exploreViewModel.showLoader(false)
                exploreViewModel.showErrorDialog(message = resources.getString(R.string.please_enable_gps))
            }
            locationHelper.locationSetUp(
                object : LocationHelper.LocationLatLng {
                    override fun getCurrentLocation(location: Location) {
                        exploreViewModel.showLoader(false)
                        val bundle = bundleOf(LOCATION_LAT to location.latitude, LOCATION_LNG to location.longitude)
                        navigate(R.id.action_exploreFragment_to_exploreMapFragment,bundle)
                        Timber.e("Current Location ${location.latitude}")
                    }
                },
                activity,locationCallback =locationCallback )
//

        }
    }
}
