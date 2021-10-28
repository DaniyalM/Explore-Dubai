package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.app.dubaiculture.databinding.FragmentDeleteDialogBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteDialogFragment : BaseBottomSheetFragment<FragmentDeleteDialogBinding>() {

    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDeleteDialogBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = tripSharedViewModel
        binding.view = this
    }

    fun onDeleteClicked() {
        tripSharedViewModel.selectedDelete()
        dismiss()
    }


}