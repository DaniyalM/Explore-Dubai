package com.dubaiculture.ui.postLogin.plantrip.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentPlanTripParentBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.TripStep1Fragment
import com.dubaiculture.ui.postLogin.plantrip.steps.step2.TripStep2Fragment
import com.dubaiculture.ui.postLogin.plantrip.steps.step3.TripStep3Fragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanTripParentFragment : BaseFragment<FragmentPlanTripParentBinding>(), CustomNavigation {

    var bottomNavigationView: BottomNavigationView? = null
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlanTripParentBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView = binding.bttmNav
        binding.view = this
        setUpNavigation()


    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()

    }

    private fun subscribeToObservables() =
        tripSharedViewModel.showPlan.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                if (it) {
                    onBackPressed()
                }
            }

//            if (it) {
//                navigate(R.id.action_tripFragment_to_my_tripFragment)
//            }
        }

    fun onBackPressed() {
        navigateBack()
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
    }

}