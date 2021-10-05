package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.dubaiculture.databinding.FragmentTripStep3Binding
import com.app.dubaiculture.databinding.FragmentTripStep4Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

class TripStep4Fragment : BaseFragment<FragmentTripStep4Binding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep4Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    fun onSelectDateClicked() {

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build()

        dateRangePicker.show(requireFragmentManager(), tag)

        dateRangePicker.addOnPositiveButtonClickListener {
            showToast("Start Date : " + DateFormat.format("dd/MM/yyyy", it.first!!)+ "End Date : " + DateFormat.format("dd/MM/yyyy", it.second!!))
        }

    }


}

