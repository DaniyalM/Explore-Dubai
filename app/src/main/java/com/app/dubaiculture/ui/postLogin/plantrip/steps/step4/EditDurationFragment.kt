package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

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
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.trip.local.Duration
import com.app.dubaiculture.databinding.FragmentEditDurationBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.EditDurationAdapter
import com.app.dubaiculture.ui.postLogin.plantrip.steps.step4.adapter.clicklisteners.DurationClickListener
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDurationFragment : BaseBottomSheetFragment<FragmentEditDurationBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditDurationBinding.inflate(inflater, container, false)

    private lateinit var durationList: List<Duration>
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    private lateinit var editDurationAdapter: EditDurationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        binding.viewModel = tripSharedViewModel
        setupRV()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()

    }

    private fun subscribeToObservables() {

        tripSharedViewModel.durationSummary.observe(viewLifecycleOwner){
            tripSharedViewModel._duration.value = it
        }

        tripSharedViewModel.duration.observe(viewLifecycleOwner) {

            if (it != null && it.isNotEmpty()) {
                durationList = it
                setData(it[0])
                binding.rvDates.visibility = View.VISIBLE
                editDurationAdapter.submitList(it.subList(1, it.size))
            }else{

            }
        }


    }

    private fun setupRV() {

        binding.rvDates.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            editDurationAdapter = EditDurationAdapter(
                object : DurationClickListener {
                    override fun rowClickListener(duration: Duration) {

                        tripSharedViewModel.updateDurationList(duration)

                    }

                    override fun rowClickListener(duration: Duration, position: Int) {
                        TODO("Not yet implemented")
                    }

                }
            )
            adapter = editDurationAdapter


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

    fun onItemSelected(duration: Duration) {

        binding.checkBoxRepeat.isChecked = false
        tripSharedViewModel.updateDurationList(duration.copy(isSelected = !duration.isSelected))

    }

    fun onDoneClicked() {

        tripSharedViewModel._durationSummary.value = durationList
        dismiss()

    }

    fun onDropDownClicked(view: View) {

        showMenu(view, R.menu.duration_menu)

    }

    fun onDeleteClicked() {

        binding.checkBoxRepeat.isChecked = false
        navigate(R.id.action_edit_duration_to_deleteBottomSheetFragment)
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

}