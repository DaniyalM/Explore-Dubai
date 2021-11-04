package com.app.dubaiculture.ui.postLogin.plantrip.steps.step2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.InterestedInType
import com.app.dubaiculture.databinding.FragmentTripStep2Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step2.adapter.InterestedInAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step2.adapter.clicklisteners.InterestedInClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.Step2ViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripStep2Fragment : BaseFragment<FragmentTripStep2Binding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private val step2ViewModel: Step2ViewModel by viewModels()
    var isChecked: Boolean = false

    private lateinit var interestedInAdapter: InterestedInAdapter


    companion object {
        lateinit var customNavigation: CustomNavigation
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep2Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        subscribeUiEvents(step2ViewModel)
        lottieAnimationRTL(binding.animationView)
        setupRV()

    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()

    }

    private fun setupRV() {

        binding.rvInterested.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            interestedInAdapter = InterestedInAdapter(
                object : InterestedInClickListener {
                    override fun rowClickListener(interestedInType: InterestedInType) {
//                        showToast(userType.title)
                    }

                    override fun rowClickListener(
                        interestedInType: InterestedInType,
                        position: Int
                    ) {

//                        step2ViewModel.updateUserItem(userType)
                        step2ViewModel.updateInterestedType(interestedInType.copy(checked = !interestedInType.checked))

                    }

                }
            )
            adapter = interestedInAdapter


        }

    }

    fun onPreviousClicked() {
        customNavigation.navigateStep(false, R.id.tripStep2)
    }

    fun onNextClicked() {
        if (step2ViewModel.validate()) {
            customNavigation.navigateStep(true, R.id.tripStep2)
        } else {
            showToast(getString(R.string.selectCategory))
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()

    }

    private fun subscribeToObservables() {

        step2ViewModel.interestedIntype.observe(viewLifecycleOwner) {
            step2ViewModel.updateInInterestedInList(it)
        }

        step2ViewModel.interestedIn.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it ->

                step2ViewModel._interestedInList.value = it.interestedInList

            }

        }

        step2ViewModel.interestedInList.observe(viewLifecycleOwner) {


            tripSharedViewModel._interestedInList.value = it
            interestedInAdapter.submitList(it)

        }

    }

}