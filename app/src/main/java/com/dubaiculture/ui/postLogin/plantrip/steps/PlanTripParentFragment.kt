package com.dubaiculture.ui.postLogin.plantrip.steps

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentPlanTripParentBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.plantrip.PlanYourTripFragmentDirections
import com.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.TripStep1Fragment
import com.dubaiculture.ui.postLogin.plantrip.steps.step2.TripStep2Fragment
import com.dubaiculture.ui.postLogin.plantrip.steps.step3.TripStep3Fragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.PlanYourTripViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class PlanTripParentFragment : BaseFragment<FragmentPlanTripParentBinding>(), CustomNavigation,
    AppBarLayout.OnOffsetChangedListener {

    var bottomNavigationView: BottomNavigationView? = null
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private val planYourTripViewModel: PlanYourTripViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlanTripParentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView = binding.bttmNav
        binding.view = this
        binding.backgroundImage =
            "/-/media/DC/DC-Attractions-New-Assets/Portrait-Images/Etihad-Museum/DX2-0266-HDR-2.jpg"
        setUpNavigation()
        binding.appbarLayout.addOnOffsetChangedListener(this)
        binding.collapsingToolbarLayout.setContentScrimColor(Color.WHITE)
        lottieAnimationRTL(binding.animationView)

//        val params: CoordinatorLayout.LayoutParams =
//            binding.appbarLayout.layoutParams as CoordinatorLayout.LayoutParams
//
//        val behavior = AppBarLayout.Behavior()
//        behavior.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
//            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
//                return false
//            }
//        })
//        params.behavior = behavior

    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        planYourTripViewModel.getTripCount()
        subscribeToObservables()

    }

    private fun subscribeToObservables() {
        tripSharedViewModel.showPlan.observe(viewLifecycleOwner)
        {
            if (it.peekContent()) {
                navigateByDirections(PlanYourTripFragmentDirections.actionTripFragmentToMyTripFragment())
            }

//            if (it) {
//                navigate(R.id.action_tripFragment_to_my_tripFragment)
//            }
        }

        planYourTripViewModel.tripCount.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (getCurrentLanguage() != Locale.ENGLISH) {
                    var nf: NumberFormat = NumberFormat.getInstance(Locale("ar"))
                    binding.tvCount.text = nf.format(it)
                } else {
                    var nf: NumberFormat = NumberFormat.getInstance(Locale("en"))
                    binding.tvCount.text = nf.format(it)
                }
            }
        }

    }

    fun onDropDownClicked() {
        binding.appbarLayout.setExpanded(true)
    }

    fun onMyTripClicked() {
        navigate(R.id.action_tripFragment_to_savedTripFragment)
    }

    fun onBackPressed() {
//        back()
        showAlert(
            message = getString(R.string.tripCloseAlert),
            textPositive = getString(R.string.yes),
            textNegative = getString(R.string.no),
            actionNegative = {

            },
            actionPositive = {
                navigateByDirections(PlanTripParentFragmentDirections.actionTripFragmentToBackFragment())
            }
        )
    }

    private fun setUpNavigation() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_trip_fragment) as NavHostFragment
        setupWithNavController(binding.bttmNav, navHostFragment.navController)
        binding.bttmNav.menu.forEach { it.isEnabled = false }

        registerCallback()


        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.tripStep1 -> {
//                    binding.bttmNav.menu[0].isEnabled = false
                    binding.tripProgressbar.progress = 25
                }
                R.id.tripStep2 -> {
//                    binding.bttmNav.menu[1].isEnabled = false
                    binding.tripProgressbar.progress = 50
                }
                R.id.tripStep3 -> {
//                    binding.bttmNav.menu[3].isEnabled = false
                    binding.tripProgressbar.progress = 75
                }
                R.id.tripStep4 -> {
//                    binding.bttmNav.menu[5].isEnabled = false

                    binding.tripProgressbar.progress = 100
                }
                else -> {
                    binding.tripProgressbar.progress = 0
                }
            }
        }

    }

    private fun registerCallback() {

        TripStep1Fragment.customNavigation = this
        TripStep2Fragment.customNavigation = this
        TripStep3Fragment.customNavigation = this


    }

    override fun navigateStep(isNext: Boolean, stepId: Int) {

        binding.bttmNav.menu.forEach { it.isEnabled = true }

        when (stepId) {
            R.id.tripStep1 -> {
                if (isNext) {

                    bottomNavigationView!!.selectedItemId = R.id.tripStep2
                }
            }
            R.id.tripStep2 -> {
                if (isNext) {
                    bottomNavigationView!!.selectedItemId = R.id.tripStep3


                } else {
                    bottomNavigationView!!.selectedItemId = R.id.tripStep1

                }
            }
            R.id.tripStep3 -> {
                if (isNext) {
                    bottomNavigationView!!.selectedItemId = R.id.tripStep4

                } else {
                    bottomNavigationView!!.selectedItemId = R.id.tripStep2

                }
            }
        }

        binding.bttmNav.menu.forEach { it.isEnabled = false }


    }


    override fun onDestroy() {
        super.onDestroy()
        tripSharedViewModel._duration.value = null
        tripSharedViewModel._durationSummary.value = null
        tripSharedViewModel._nearestLocationType.value = null
        tripSharedViewModel._nearestLocationTemp.value = null
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

        if (verticalOffset == 0) {

            binding.toolbar.hide()
            binding.ivClosetl.show()

        } else {
            binding.toolbar.show()
            binding.ivClosetl.hide()


        }

    }

}