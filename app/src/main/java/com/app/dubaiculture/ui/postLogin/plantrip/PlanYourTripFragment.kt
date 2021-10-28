package com.app.dubaiculture.ui.postLogin.plantrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentPlanYourTripBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanYourTripFragment : BaseFragment<FragmentPlanYourTripBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPlanYourTripBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        lottieAnimationRTL(binding.animationView)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun subscribeToObservables() =
        tripSharedViewModel.showPlan.observe(viewLifecycleOwner) {
//            it?.getContentIfNotHandled()?.let {
//                if (it){
//                    navigateByDirections(PlanYourTripFragmentDirections.actionTripFragmentToMyTripFragment())
//                }
//            }

            if (it.peekContent()) {
                navigateByDirections(PlanYourTripFragmentDirections.actionTripFragmentToMyTripFragment())
            }
        }

    fun onBottomSheetClicked() {

        navigate(R.id.action_tripFragment_to_tripDetailParentFragment)

    }

    fun onBackPressed() {
        back()
    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()

    }

}