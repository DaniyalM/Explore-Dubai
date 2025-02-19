package com.dubaiculture.ui.postLogin.plantrip.steps.step3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.LocationNearest
import com.dubaiculture.databinding.FragmentAddLocationBottomsheetBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel

class AddLocationBottomSheetFragment :
    BaseBottomSheetFragment<FragmentAddLocationBottomsheetBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
    }

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

//        tripSharedViewModel.nearestLocation.observe(viewLifecycleOwner) {
//
//            tripSharedViewModel._usersType.value = it.nearestLocation
//
//        }

        tripSharedViewModel.type.observe(viewLifecycleOwner) {
            tripSharedViewModel.updateInLocationList(it)
        }



        tripSharedViewModel.nearestLocation.observe(viewLifecycleOwner) {
            if (it != null) {
                setAdapter(it)
            }

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

            tripSharedViewModel.updateLocationItem(nearestLocation.single { locationNearest ->
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