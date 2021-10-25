package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.data.repository.trip.local.InterestedInType
import com.app.dubaiculture.databinding.FragmentAddDurationBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step2.adapter.InterestedInAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step2.adapter.clicklisteners.InterestedInClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.DurationAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDurationFragment : BaseBottomSheetFragment<FragmentAddDurationBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddDurationBinding.inflate(inflater, container, false)

    private lateinit var durationList: List<Duration>
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private lateinit var durationAdapter: DurationAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = tripSharedViewModel
        setupRV()

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
            )
            adapter = durationAdapter


        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun subscribeToObservables() {

        tripSharedViewModel.duration.observe(viewLifecycleOwner) {

            durationList = it
            setData(it[0])
            binding.rvDates.visibility = View.VISIBLE
            durationAdapter.submitList(it.subList(1, it.size))
        }

    }

    private fun setData(duration: Duration) {
        binding.data = duration

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

                binding.btnDay.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_650)
                )

                binding.btnNight.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_night)

                binding.btnNight.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.transparent)
                )
            }
            2 -> {
                binding.btnNight.icon =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_night_selected)

                binding.btnNight.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.purple_650)
                )

                binding.btnDay.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_day)

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

    fun onDoneClicked(){

        back()

    }

}