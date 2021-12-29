package com.dubaiculture.ui.postLogin.plantrip.mytrip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dubaiculture.R
import com.dubaiculture.databinding.DialogDeleteTripBinding
import com.dubaiculture.databinding.DialogMyTripNameBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.more.about.WebViewFragmentArgs
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.MyTripViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripDeleteViewModel
import com.dubaiculture.ui.postLogin.plantrip.viewmodels.TripSharedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteTripDialog : BaseBottomSheetFragment<DialogDeleteTripBinding>() {

    private val deleteViewModel: TripDeleteViewModel by viewModels()
    private val tripSharedViewModel: TripSharedViewModel by activityViewModels()
    val args: DeleteTripDialogArgs by navArgs()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = DialogDeleteTripBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backArrowRTLChild(binding.arrow)
        binding.tripId = args.tripId
        binding.view = this
        subscribeUiEvents(deleteViewModel)
        binding.viewModel = deleteViewModel

        binding.cvDriving.setOnClickListener {
            showConfirmationDialog(
                callback = {
                    deleteViewModel.deleteTrip(args.tripId)
                }
            )
        }
    }

    private fun showConfirmationDialog(callback: () -> Unit) {
        showAlert(
            message = getString(R.string.are_you_sure_want_to_delete_trip),
            textPositive = getString(R.string.yes),
            textNegative = getString(R.string.no),
            actionPositive = {
                callback()
            }
        )
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()

    }

    private fun subscribeToObservables() {

        deleteViewModel.deleteTripStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                if (it) {
                    tripSharedViewModel.updateTripItem(args.tripId)
                    navigateByDirections(DeleteTripDialogDirections.actionDeleteSavedTrip())
                }
            }
        }

    }

    fun backArrowRTLChild(img: ImageView) {
        when {
            isArabic() -> {
                img.rotation = -180f
            }

        }
    }


}