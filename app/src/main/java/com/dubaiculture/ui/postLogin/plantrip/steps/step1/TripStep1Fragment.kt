package com.dubaiculture.ui.postLogin.plantrip.steps.step1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.UsersType
import com.dubaiculture.databinding.FragmentTripStep1Binding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.UserTypeAdapter
import com.dubaiculture.ui.postLogin.plantrip.steps.step1.adapter.clicklisteners.UserTypeClickListener
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.Step1ViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripStep1Fragment : BaseFragment<FragmentTripStep1Binding>() {

    private val step1ViewModel: Step1ViewModel by viewModels()
    private val tripSharedViewModel: TripSharedViewModel by navGraphViewModels(R.id.plan_trip_parent_navigation)
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
        subscribeUiEvents(step1ViewModel)
        binding.viewModel = step1ViewModel
        binding.step1View = this



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
                        step1ViewModel.updateUserItem(userType.copy(checked = !userType.checked!!))
                    }

                }
            )
            adapter = userTypeAdapter


        }

    }

    private fun subscribeToObservables() {

        step1ViewModel.userType.observe(viewLifecycleOwner) {
            step1ViewModel._usersType.value = it.usersType
        }

        step1ViewModel.type.observe(viewLifecycleOwner) {
            step1ViewModel.updateInUserTypeList(it)
        }

        step1ViewModel.usersType.observe(viewLifecycleOwner) {
            userTypeAdapter.submitList(it)
        }

    }


    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()
    }

    fun onNextClicked() {

        if (step1ViewModel.validate()) {
            customNavigation.navigateStep(true, R.id.tripStep1)
        } else {
            showToast(getString(R.string.selectUserType))
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()

    }

}