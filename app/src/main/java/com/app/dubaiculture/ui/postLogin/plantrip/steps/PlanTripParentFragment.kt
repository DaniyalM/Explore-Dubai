package com.app.dubaiculture.ui.postLogin.plantrip.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPlanTripParentBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class PlanTripParentFragment : BaseFragment<FragmentPlanTripParentBinding>() {

    private var bottomNavigationView: BottomNavigationView? = null

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

    fun onBackPressed() {
            navigateBack()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_trip_fragment) as NavHostFragment
        setupWithNavController(binding.bttmNav, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.tripStep1 -> {
                    binding.tripProgressbar.progress = 25
                }
                R.id.tripStep2 -> {
                    binding.tripProgressbar.progress = 50
                }
                R.id.tripStep3 -> {

                    binding.tripProgressbar.progress = 75
                }
                R.id.tripStep4 -> {

                    binding.tripProgressbar.progress = 100
                }
                else ->{
                    binding.tripProgressbar.progress = 0
                }
            }
        }

    }


}