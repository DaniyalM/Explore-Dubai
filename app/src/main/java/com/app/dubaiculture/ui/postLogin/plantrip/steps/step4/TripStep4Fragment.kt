package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.databinding.FragmentTripStep4Binding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.DurationSummaryAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.Step4ViewModel
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.app.dubaiculture.utils.event.Event
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TripStep4Fragment : BaseFragment<FragmentTripStep4Binding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private val step4ViewModel: Step4ViewModel by viewModels()

    private lateinit var durationSummaryAdapter: DurationSummaryAdapter


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTripStep4Binding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = tripSharedViewModel
        binding.step4ViewModel = step4ViewModel
        lottieAnimationRTL(binding.animationView)
        subscribeUiEvents(step4ViewModel)
        setupRV()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun subscribeToObservables() {

        tripSharedViewModel.durationSummary.observe(viewLifecycleOwner) {
            if (it != null)
                durationSummaryAdapter.submitList(it)
        }


        step4ViewModel.eventAttraction.observe(viewLifecycleOwner) {
            tripSharedViewModel._showPlan.value = Event(true)
        }

        tripSharedViewModel.duration.observe(viewLifecycleOwner) {

            if (tripSharedViewModel._durationSummary.value == null && tripSharedViewModel._duration.value != null)
                navigate(R.id.action_step4_to_durationBottomSheetFragment)

//            durationSummaryAdapter.submitList(it)

        }

        tripSharedViewModel.eventAttraction.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { it1 -> step4ViewModel.postEventAttraction(it1) }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.animationView.playAnimation()

    }

    fun onNoDaysClicked() {

        navigate(R.id.action_step4_to_noDaysBottomSheetFragment)

    }

//    fun onShowMyPlanClicked() {
//
//
//    }

    fun onEditDurationClicked() {
        navigate(R.id.action_step4_to_edit_durationBottomSheetFragment)
    }

    fun onSelectDateClicked() {

        val constraintBuilder: CalendarConstraints.Builder = CalendarConstraints.Builder()
        constraintBuilder.setValidator(DateValidatorPointForward.now())

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setTheme(R.style.ThemeOverlay_App_DatePicker)
                .setCalendarConstraints(constraintBuilder.build())
                .build()

        dateRangePicker.show(requireFragmentManager(), tag)

        dateRangePicker.addOnPositiveButtonClickListener {


            tripSharedViewModel.getDaysList(
                DateFormat.format(
                    "dd MMM,yy",
                    it.first!!
                ).toString(), DateFormat.format("dd MMM,yy", it.second!!).toString()

            )


        }

    }

    private fun setupRV() {

        binding.rvDurations.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            durationSummaryAdapter = DurationSummaryAdapter(
                object : DurationClickListener {
                    override fun rowClickListener(duration: Duration) {

                    }

                    override fun rowClickListener(duration: Duration, position: Int) {
                        TODO("Not yet implemented")
                    }

                }
            )
            adapter = durationSummaryAdapter


        }

    }


}
