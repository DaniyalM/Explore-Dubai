package com.app.dubaiculture.ui.postLogin.plantrip.steps.step3

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.observe
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.LocationNearest
import com.app.dubaiculture.data.repository.trip.local.NearestLocation
import com.app.dubaiculture.databinding.FragmentAddLocationBottomsheetBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.app.dubaiculture.utils.event.EventUtilFunctions
import com.app.dubaiculture.utils.Constants.NavBundles.NEAREST_LOCATION

class AddLocationBottomSheetFragment :
    BaseBottomSheetFragment<FragmentAddLocationBottomsheetBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddLocationBottomsheetBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }


    private fun subscribeToObservables() {

        tripSharedViewModel.nearestLocation.observe(viewLifecycleOwner) {

            tripSharedViewModel._usersType.value = it.nearestLocation

        }

        tripSharedViewModel.type.observe(viewLifecycleOwner) {
            tripSharedViewModel.updateInUserTypeList(it)
        }



        tripSharedViewModel.usersType.observe(viewLifecycleOwner) {

            setAdapter(it)

        }

    }

    private fun setAdapter(nearestLocation: List<LocationNearest>) {

        val locAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            nearestLocation.map { it.locationTitle }
        )
        binding.editTripName.setAdapter(locAdapter)

        binding.editTripName.setOnItemClickListener { adapterView, view, i, l ->

            tripSharedViewModel.updateUserItem(nearestLocation.single { locationNearest ->
                locationNearest.locationTitle == adapterView.getItemAtPosition(
                    i
                ).toString()
            }.copy(isChecked = true))


        }

    }

    fun onContinueClicked() {
        back()
    }

}