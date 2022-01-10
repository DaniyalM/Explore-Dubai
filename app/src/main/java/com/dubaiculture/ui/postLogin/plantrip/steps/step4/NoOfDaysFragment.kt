package com.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentNoDaysBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.google.android.material.chip.Chip
import timber.log.Timber


class NoOfDaysFragment : BaseBottomSheetFragment<FragmentNoDaysBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoDaysBinding.inflate(inflater, container, false)

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
        subscribeToObservable()
    }


    private fun addChip(title: String, index: Int) {
        val chip = layoutInflater.inflate(
            R.layout.no_of_days_chip_layout,
            binding.groupDaysChips,
            false
        ) as Chip
        chip.text = title
        chip.id = index
        binding.groupDaysChips.addView(chip)
    }


    fun onDoneClicked() {

        val selectedChips = binding.groupDaysChips.children
            .filter { (it as Chip).isChecked }
            .map { (it as Chip).text.toString() }
        selectedChips.iterator().forEach {
            back()
//            it.split(" ")
//            Integer.parseInt(it.split("\\s+")[0])
            val splited = it.split("\\s+")
            tripSharedViewModel.getList(it.substring(0, it.indexOf(" ")).toInt())
            navigateByDirections(NoOfDaysFragmentDirections.actionDaysToDurationBottomSheetFragment())

        }


//
//        back()

//        when (binding.groupDaysChips.checkedChipId) {
//

//            binding.choice1.id -> {
//                tripSharedViewModel.getList(2)
//
////                navigate(R.id.action_days_to_durationBottomSheetFragment)
//            }
//            binding.choice2.id -> {
//                tripSharedViewModel.getList(4)
//
////                navigate(R.id.action_days_to_durationBottomSheetFragment)
//
//            }
//            binding.choice3.id -> {
//                tripSharedViewModel.getList(6)
//
////                navigate(R.id.action_days_to_durationBottomSheetFragment)
//
//            }
//            binding.choice4.id -> {
//                tripSharedViewModel.getList(8)
//
////                navigate(R.id.action_days_to_durationBottomSheetFragment)
//
//            }
//            binding.choice5.id -> {
//                tripSharedViewModel.getList(10)
//
////                navigate(R.id.action_days_to_durationBottomSheetFragment)
//
//            }
//        }


    }

    private fun subscribeToObservable() {
        tripSharedViewModel.addDurationList.observe(viewLifecycleOwner) {
            it.daysList.forEachIndexed { index, day ->
                addChip(title = day.duration, index = index)
            }
        }
    }


}