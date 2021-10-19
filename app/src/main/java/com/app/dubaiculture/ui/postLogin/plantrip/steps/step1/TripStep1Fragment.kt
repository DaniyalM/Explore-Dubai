package com.app.dubaiculture.ui.postLogin.plantrip.steps.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.UserTypes
import com.app.dubaiculture.data.repository.trip.local.UsersType
import com.app.dubaiculture.databinding.FragmentTripStep1Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.LatestNewsListAdapter
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.app.dubaiculture.ui.postLogin.plantrip.steps.PlanTripParentFragment
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.UserTypeAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.clicklisteners.UserTypeClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.Step1ViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripStep1Fragment : BaseFragment<FragmentTripStep1Binding>() {

    private val step1ViewModel: Step1ViewModel by viewModels()
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()

    private lateinit var userTypeAdapter: UserTypeAdapter


    companion object {
        lateinit var customNavigation: CustomNavigation
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep1Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = tripSharedViewModel
        lottieAnimationRTL(binding.animationView)
        setupRV()

    }

    private fun setupRV() {

        binding.rvUserType.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            userTypeAdapter = UserTypeAdapter(
                object : UserTypeClickListener {
                    override fun rowClickListener(userType: UsersType) {
//                        showToast(userType.title)
                    }

                    override fun rowClickListener(userType: UsersType, position: Int) {
                        showToast(userType.id.toString())
                        userType.checked = true
                        tripSharedViewModel.updateUserItem(userType)
//                        userTypeAdapter.notifyDataSetChanged()
                    }

                }
            )
            adapter = userTypeAdapter


        }

    }

    private fun subscribeToObservables() {
//        step1ViewModel.userType.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { it ->
//
//                userTypeAdapter.submitList(it.usersType)
//
//            }
//
//        }
        tripSharedViewModel.userType.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { it ->
//
//                userTypeAdapter.submitList(it.usersType)
//
//            }

            it.peekContent().let { it ->
                userTypeAdapter.submitList(it.usersType)
            }

        }
    }


    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()

    }

    fun onNextClicked() {

        customNavigation.navigateStep(true, R.id.tripStep1)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()

    }

}