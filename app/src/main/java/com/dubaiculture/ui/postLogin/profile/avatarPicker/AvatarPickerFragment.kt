package com.dubaiculture.ui.postLogin.profile.avatarPicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dubaiculture.databinding.FragmentImagePickerBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.profile.service.ImagePickerService
import com.dubaiculture.ui.postLogin.profile.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AvatarPickerFragment : BaseBottomSheetFragment<FragmentImagePickerBinding>() {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentImagePickerBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(profileViewModel)
        binding.apply {
            cameraPickerContainer.setOnClickListener {
                bus.post(ImagePickerService.CameraClick)
                dismiss()
            }

            galleryPickerContainer.setOnClickListener {
                bus.post(ImagePickerService.GalleryClick)
                dismiss()
            }
        }
    }


}