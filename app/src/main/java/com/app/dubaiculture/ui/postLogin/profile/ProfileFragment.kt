package com.app.dubaiculture.ui.postLogin.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.profile.local.Favourite
import com.app.dubaiculture.data.repository.profile.utils.ImageFilePath
import com.app.dubaiculture.databinding.FragmentProfileBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.profile.service.ImagePickerService
import com.app.dubaiculture.ui.postLogin.profile.viewmodels.ProfileSharedViewModel
import com.app.dubaiculture.ui.postLogin.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.FileUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.RESULT_ERROR
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getError
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private val profileSharedViewModel: ProfileSharedViewModel by activityViewModels()

    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var favourite: Favourite


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentProfileBinding.inflate(inflater, container, false)

    private fun subscribeToObservables() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profileSharedViewModel.favourite.collectLatest {
                favourite = it
                binding.badgeText.text =
                    "${it.events.size + it.attractions.size + it.services.size}"
            }
        }

//        profileViewModel.favourite.observe(viewLifecycleOwner) {
//            when (it) {
//                is Result.Success -> {
//                    it.value.getContentIfNotHandled()?.let {
//
//                    }
//                }
//                is Result.Failure -> handleApiError(it, profileViewModel)
//            }
//        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateRTL()

        if (!this::favourite.isInitialized)
            profileSharedViewModel.getFavourites()

        if (!this::startForResult.isInitialized) {

            binding.user = application.auth.user
            subscribeUiEvents(profileViewModel)
            subscribeToObservables()
            registerForActivityResult()

            binding.apply {
                clMyTrips.setOnClickListener {
                    navigate(R.id.action_profileFragment_to_myTripFragment)

                }
                myServicesView.setOnClickListener {
                    navigate(R.id.action_profileFragment_to_myServicesFragment)
                }

                frameLayoutImagePicker.setOnClickListener {
                    navigate(R.id.action_profileFragment_to_avatarPickerFragment)
                }
                placesVisitedView.setOnClickListener {
                    navigate(R.id.action_profileFragment_to_placesVisited)
                }
                myEventsView.setOnClickListener {

//                    navigate(R.id.action_profileFragment_to_serviceDetailFragment)
                    navigate(R.id.action_profileFragment_to_myEventsFragment)
                }
//                editProfile.setOnClickListener {
////                    navigate(R.id.action_profileFragment_to_editProfileFragment)
//                }
                changePasswordView.setOnClickListener {
                    navigate(R.id.action_profileFragment_to_passwordChangeFragment)
                }
                favouriteContainer.setOnClickListener {
                    navigate(R.id.action_profileFragment_to_favouriteFragment)
//                    if (this@ProfileFragment::favourite.isInitialized) {
//                        navigate(R.id.action_profileFragment_to_favouriteFragment, Bundle().apply {
//                            putParcelable(FAVOURITE_BUNDLE, favourite)
//                        })
//                    } else {
//
//                    }

                }


            }
        }


    }

    fun initiateRTL() {
        binding.apply {
            header.back.setOnClickListener {
                back()
            }
            lottieAnimationRTL(animationView)
            backArrowRTL(header.back)
        }
    }

    fun backArrowRTLProfile(img: ImageView) {
        when {
            isArabic() -> {
                img.rotation = 180f
            }
            else -> {
                img.rotation = 0f
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
                    val fileUri = data?.data!!

//                mProfileUri = fileUri


                    fileUri.apply {
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
                    }
                    binding.profileImage.setImageURI(fileUri)
                } else if (resultCode == RESULT_ERROR) {
                    profileViewModel.showToast(getError(data))
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