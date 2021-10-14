package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentTripStep3Binding
import com.app.dubaiculture.databinding.FragmentTripStep4Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.callback.CustomNavigation
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.app.dubaiculture.utils.event.Event
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TripStep4Fragment : BaseFragment<FragmentTripStep4Binding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep4Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        lottieAnimationRTL(binding.animationView)

    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()

    }

    fun onNoDaysClicked() {

        navigate(R.id.action_step4_to_noDaysBottomSheetFragment)

    }

    fun onShowMyPlanClicked() {

        tripSharedViewModel._showPlan.value = Event(true)

    }

    fun onEditDurationClicked() {
        navigate(R.id.action_step4_to_edit_durationBottomSheetFragment)
    }

    fun onSelectDateClicked() {

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .build()

        dateRangePicker.show(requireFragmentManager(), tag)

        dateRangePicker.addOnPositiveButtonClickListener {
            showToast(
                "Start Date : " + DateFormat.format(
                    "dd/MM/yyyy",
                    it.first!!
                ) + "End Date : " + DateFormat.format("dd/MM/yyyy", it.second!!)
            )

            navigate(R.id.action_step4_to_durationBottomSheetFragment)


        }

    }


}

