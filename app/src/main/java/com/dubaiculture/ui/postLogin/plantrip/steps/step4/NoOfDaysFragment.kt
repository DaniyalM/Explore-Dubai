package com.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dubaiculture.databinding.FragmentNoDaysBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel

class NoOfDaysFragment : BaseBottomSheetFragment<FragmentNoDaysBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNoDaysBinding.inflate(inflater, container, false)

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    fun onDoneClicked() {

        back()

        when (binding.groupDaysChips.checkedChipId) {
            binding.choice1.id -> {
                tripSharedViewModel.getList(2)

//                navigate(R.id.action_days_to_durationBottomSheetFragment)
            }
            binding.choice2.id -> {
                tripSharedViewModel.getList(4)

//                navigate(R.id.action_days_to_durationBottomSheetFragment)

            }
            binding.choice3.id -> {
                tripSharedViewModel.getList(6)

//                navigate(R.id.action_days_to_durationBottomSheetFragment)

            }
            binding.choice4.id -> {
                tripSharedViewModel.getList(8)

//                navigate(R.id.action_days_to_durationBottomSheetFragment)

            }
            binding.choice5.id -> {
                tripSharedViewModel.getList(10)

//                navigate(R.id.action_days_to_durationBottomSheetFragment)

            }
        }


    }


}