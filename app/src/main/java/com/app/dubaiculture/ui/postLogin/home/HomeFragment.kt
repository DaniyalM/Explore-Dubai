package com.app.dubaiculture.ui.postLogin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentHomeBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.home.viewmodels.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    private var bottomNavigationView: BottomNavigationView? = null
//    private var currentNavController: LiveData<NavController>? = null


    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView = binding.bottomNav
        applicationExitDialog()
        setupBottomNavVisibility()
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
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

    private fun getNavController(): NavController {
        val navHostFragment =
                childFragmentManager.findFragmentById(R.id.nav_home_container_view) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupBottomNavVisibility() {

        bottomNavigationView?.apply {
            val navigationController=getNavController()
            setupWithNavController(navigationController)
            navigationController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.threeSixtyFragment -> {
                        visibility = View.GONE
                    }
                    R.id.registrationSuccessFragment2 -> {
                        visibility = View.GONE
                    }
                    R.id.ARFragment -> {
                        visibility = View.GONE
                    }
                    R.id.siteMapFragment -> {
                        visibility = View.GONE
                    }
                    R.id.ibeconFragment -> {
                        visibility = View.GONE
                    }
                    R.id.attractionGalleryFragment -> {
                        visibility = View.GONE
                    }
                    R.id.myEventsFragment -> {
                        visibility = View.GONE
                    }
//                R.id.placesVisited -> {
//                    bottomNav.visibility = View.GONE
//                }
                    R.id.notificationFragment -> {
//                    bottomNav.visibility = View.GONE
                    }
                    R.id.favouriteFragment -> {
                        visibility = View.GONE
                    }
                    else -> {
                        visibility = View.VISIBLE
                    }
                }

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

}