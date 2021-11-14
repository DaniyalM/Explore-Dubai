package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.databinding.DialogMyTripNameBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.MyTripViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.SaveTripViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTripNameDialog : BaseBottomSheetFragment<DialogMyTripNameBinding>() {

    private val viewModel: SaveTripViewModel by viewModels()
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogMyTripNameBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        subscribeUiEvents(viewModel)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun subscribeToObservables() {

        tripSharedViewModel.eventAttractionResponse.observe(viewLifecycleOwner) {
            if (it != null)
                binding.tripId = it.tripId
        }

        viewModel.saveTripStatus.observe(viewLifecycleOwner) {
            navigate(R.id.action_trip_name_to_tripsuccessFragment)
        }

    }

}