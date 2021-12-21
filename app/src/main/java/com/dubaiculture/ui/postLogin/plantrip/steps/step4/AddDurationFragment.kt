package com.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.trip.local.Duration
import com.dubaiculture.data.repository.trip.local.Durations
import com.dubaiculture.databinding.FragmentAddDurationBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.DurationAdapter
import com.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.dubaiculture.utils.ColorUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDurationFragment : BaseBottomSheetFragment<FragmentAddDurationBinding>() {


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddDurationBinding.inflate(inflater, container, false)

    private lateinit var durationList: List<Duration>
    private lateinit var addDurationList: Durations
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private lateinit var durationAdapter: DurationAdapter
    var states = arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_pressed)
    )

    var colors = intArrayOf(
        Color.WHITE,
        Color.WHITE,
        Color.WHITE,
        Color.WHITE
    )

    var unSelectedStates = arrayOf(
        intArrayOf(android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_enabled),
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_pressed)
    )



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = tripSharedViewModel

    }

    fun onDropDownClicked(view: View) {

        showMenu(view, R.menu.duration_menu)

    }

    private fun setupRV() {

        binding.rvDates.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            durationAdapter = DurationAdapter(
                object : DurationClickListener {
                    override fun rowClickListener(duration: Duration) {
                        tripSharedViewModel.updateDurationList(duration)
                    }

                    override fun rowClickListener(duration: Duration, position: Int) {
                        TODO("Not yet implemented")
                    }

                }
            ,addDurationList)
            adapter = durationAdapter


        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()

    }

    private fun subscribeToObservables() {
        tripSharedViewModel.addDurationList.observe(viewLifecycleOwner) {
            addDurationList = it
            setupRV()

        }

        tripSharedViewModel.duration.observe(viewLifecycleOwner) {

            if (it != null) {
                durationList = it
                setData(it[0])
                binding.rvDates.visibility = View.VISIBLE
                durationAdapter.submitList(it.subList(1, it.size))
            }
        }


    }

    private fun setData(duration: Duration) {
        binding.data = duration

        var unSelectedColors = intArrayOf(
            ColorUtil.fetchColor(requireContext(),R.attr.colorSecondaryVariant),
            ColorUtil.fetchColor(requireContext(),R.attr.colorSecondaryVariant),
            ColorUtil.fetchColor(requireContext(),R.attr.colorSecondaryVariant),
            ColorUtil.fetchColor(requireContext(),R.attr.colorSecondaryVariant),
        )

        when (duration.isDay) {
            0 -> {
                binding.btnDay.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_day)

                binding.btnDay.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.transparent)
                )

                binding.btnNight.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_night)

                binding.btnNight.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.transparent)
                )
            }
            1 -> {
                binding.btnDay.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_day_selected)

                binding.btnDay.iconTint = ColorStateList(states, colors)

                binding.btnDay.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_650)
                )

                binding.btnNight.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_night)

                binding.btnNight.iconTint = ColorStateList(unSelectedStates, unSelectedColors)


                binding.btnNight.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.transparent)
                )
            }
            2 -> {
                binding.btnNight.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_night_selected)

                binding.btnNight.iconTint = ColorStateList(states, colors)


                binding.btnNight.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_650)
                )

                binding.btnDay.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_day)

                binding.btnDay.iconTint = ColorStateList(unSelectedStates, unSelectedColors)


                binding.btnDay.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.transparent)
                )
            }
        }

    }

    fun onDaySelected(duration: Duration) {
        binding.checkBoxRepeat.isChecked = false
        tripSharedViewModel.updateDurationList(duration.copy(isDay = 1))
    }

    fun onNightSelected(duration: Duration) {

        binding.checkBoxRepeat.isChecked = false
        tripSharedViewModel.updateDurationList(duration.copy(isDay = 2))

    }



    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        addDurationList.hoursList.forEach {
            popup.menu.add(it.duration)
        }


        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            binding.checkBoxRepeat.isChecked = false
            tripSharedViewModel.updateDurationList(binding.data!!.copy(hour = menuItem.title.toString()))
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    fun onDoneClicked() {
        if(durationList.isEmpty()){
            tripSharedViewModel._durationSummary.value = null
        }else{
            tripSharedViewModel._durationSummary.value = durationList as ArrayList<Duration>
        }
        dismiss()

    }

}