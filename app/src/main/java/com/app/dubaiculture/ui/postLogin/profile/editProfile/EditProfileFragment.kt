package com.app.dubaiculture.ui.postLogin.profile.editProfile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.profile.utils.ImageFilePath
import com.app.dubaiculture.databinding.FragmentEditProfileBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.profile.service.ImagePickerService
import com.app.dubaiculture.ui.postLogin.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.FileUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var startForResult: ActivityResultLauncher<Intent>
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditProfileBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateRTL()

        if (!this::startForResult.isInitialized) {
            binding.user = application.auth.user
        }
        registerForActivityResult()

        binding.apply {
            frameLayoutImagePicker.setOnClickListener {
                navigate(R.id.action_editProfileFragment_to_avatarPickerFragment)
            }
        }

    }

    fun initiateRTL() {
        binding.apply {
            header.back.apply {
                setOnClickListener {
                    back()
                }
                backArrowRTL(this)
            }
        }
    }

    private fun registerForActivityResult() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    data?.data!!.apply {
                        val fileSize = FileUtils().calculateFileSize(File(this.path))
                        if (fileSize > Constants.ImagePicker.IMAGE_SIZE_LIMIT)
                            showAlert(
                                "File size exceeded %smb limit".format(
                                    Constants.ImagePicker.IMAGE_SIZE_LIMIT
                                )
                            )
                        else
                            profileViewModel.uploadProfile(
                                ImageFilePath.getPath(
                                    requireActivity(),
                                    this
                                ), application
                            )
                        binding.profileImage.setImageURI(this)
                    }

                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    profileViewModel.showToast(ImagePicker.getError(data))
                } else {
                    profileViewModel.showToast("Task Cancelled")
                }
            }

    }

    @Subscribe
    fun getCameraClick(imagePickerService: ImagePickerService) {
        when (imagePickerService) {
            is ImagePickerService.CameraClick -> {
                ImagePicker.with(activity).crop()
                    .cameraOnly()
                    .compress(1024)         //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    )  //Final image resolution will be less than 1080 x 1080(Optional)
                    .createIntent { intent ->
                        startForResult.launch(intent)
                    }
            }
            is ImagePickerService.GalleryClick -> {
                ImagePicker.with(activity).crop()
                    .galleryOnly()
                    .compress(1024)         //Final image size will be less than 1 MB(Optional)
                    .maxResultSize(
                        1080,
                        1080
                    )  //Final image resolution will be less than 1080 x 1080(Optional)
                    .createIntent { intent ->
                        startForResult.launch(intent)
                    }
            }
        }
    }


}