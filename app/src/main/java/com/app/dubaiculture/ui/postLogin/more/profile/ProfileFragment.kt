package com.app.dubaiculture.ui.postLogin.more.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentProfileBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.profile.service.ImagePickerService
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.RESULT_ERROR
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.getError
import com.squareup.otto.Subscribe
import com.theartofdev.edmodo.cropper.CropImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_back.view.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val profileViewModel: ProfileViewModel by viewModels()

    private lateinit var startForResult: ActivityResultLauncher<Intent>

    private val cropActivityResultContract = object : ActivityResultContract<Intent, Uri?>() {

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return CropImage.getActivityResult(intent)?.uri
        }

        override fun createIntent(context: Context, input: Intent?): Intent {
            return CropImage.activity().setAspectRatio(16, 9).getIntent(activity)
        }

    }


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentProfileBinding.inflate(inflater, container, false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateRTL()
        subscribeUiEvents(profileViewModel)
        subscribeToObservables()
        registerForActivityResult()
        binding.apply {


            frameLayoutImagePicker.setOnClickListener {
                navigate(R.id.action_profileFragment_to_avatarPickerFragment)
            }
            placesVisitedView.setOnClickListener {
                navigate(R.id.action_profileFragment_to_placesVisited)
            }
            myEventsView.setOnClickListener {
                navigate(R.id.action_profileFragment_to_myEventsFragment)
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
            backArrowRTLProfile(arrowFavourite)
            backArrowRTLProfile(arrowChangePassword)
            backArrowRTLProfile(arrowEvents)
            backArrowRTLProfile(arrowMyServices)
            backArrowRTLProfile(arrowMyTrips)
            backArrowRTLProfile(arrowPlacesVisited)
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

    private fun subscribeToObservables(){

    }


    private fun registerForActivityResult() {
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

//                mProfileUri = fileUri
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
            is ImagePickerService.cameraClick -> {
                ImagePicker.with(activity).crop()
                        .cameraOnly()
                        .compress(1024)         //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                        .createIntent { intent ->
                            startForResult.launch(intent)
                        }
            }
            is ImagePickerService.galleryClick -> {
                ImagePicker.with(activity).crop()
                        .galleryOnly()
                        .compress(1024)         //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                        .createIntent { intent ->
                            startForResult.launch(intent)
                        }
            }
        }
    }}