package com.dubaiculture.ui.postLogin.events.detail.registernow

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.dubaiculture.R
import com.dubaiculture.data.repository.event.local.models.schedule.EventScheduleItemsSlots
import com.dubaiculture.databinding.FragmentRegisterNowBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.events.detail.helper.MultipartFormHelper
import com.dubaiculture.ui.postLogin.events.detail.registernow.attachmentOptions.AttachmentOptionFragment
import com.dubaiculture.ui.postLogin.events.detail.registernow.viewmodel.RegisterNowViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.Constants.PHOTO.REQUEST_PDF_PICKER
import com.dubaiculture.utils.FileUtils
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import java.io.IOException


@AndroidEntryPoint
class RegisterNowFragment : BaseFragment<FragmentRegisterNowBinding>(), View.OnClickListener {
    private val fileUtil = FileUtils()
    var currentPhotoPath: String = ""
    private var imagesURI: String? = ""
    private val registerNowViewModel: RegisterNowViewModel by viewModels()
    private var slotList = ArrayList<EventScheduleItemsSlots>()
    private var eventId: String? = null
    private var selectedId: String? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.apply {
            slotList =
                getParcelableArrayList<EventScheduleItemsSlots>(Constants.NavBundles.SCHEDULE_ITEM_SLOT)!!
            eventId = getString(Constants.NavBundles.EVENT_ID)
        }
        setTypeList(slotList)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener(this)
        binding.header.back.setOnClickListener(this)
        backArrowRTL(binding.header.back)

        binding.tvTermCondition.setOnClickListener {
            val bundle =
                bundleOf(Constants.NavBundles.TERMS_CONDITION_PRIVACY_POLICY to Constants.NavBundles.TERMS_CONDITION)
            navigate(
                R.id.action_eventregisterFragment_to_privacyTermConditionFragment,
                bundle
            )
        }

        binding.editTime.setOnClickListener {
            hideKeyboard(activity)
            binding.editTime.showDropDown()
        }
        binding.editTime.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.adapter.getItem(position) as EventScheduleItemsSlots
            selectedId = selectedItem.slotId
//            feedbackViewModel.setSelectedType(selectedItem.id.toIntOrNull())
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentRegisterNowBinding.inflate(inflater, container, false)


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(registerNowViewModel)

        binding.tvUpload.setOnClickListener {
            activity.runWithPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ) {
                val attachmentOption = AttachmentOptionFragment(object :
                    AttachmentOptionFragment.AttachmentOptionClickListener {
                    override fun onOptionSelection(position: Int) {
                        if (position == 1) {
                            pickPicturesFromGallery()
                        } else {
                            val intent = Intent(activity, FilePickerActivity::class.java)
                            intent.putExtra(
                                FilePickerActivity.CONFIGS, Configurations.Builder()
                                    .setCheckPermission(true)
                                    .setShowFiles(true)
                                    .setShowImages(false)
                                    .setShowAudios(false)
                                    .setShowVideos(false)
                                    .setMaxSelection(1)
                                    .setSuffixes("pdf")
                                    .setSkipZeroSizeFiles(true)
                                    .build()
                            )
                            startActivityForResult(intent, REQUEST_PDF_PICKER)
                        }
                    }
                })
                showBottomSheet(attachmentOption)
//
            }
        }

        registerNowViewModel.isRegistered.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                if (it == true) {
                    val bundle = bundleOf(
                        "key" to "RegisterNow"
                    )
                    navigate(
                        R.id.action_registerNowFragment_to_registrationSuccessFragment3,
                        bundle
                    )
                }
            }
        }
    }

    private fun setTypeList(list: List<EventScheduleItemsSlots>) {
        val adapter = ArrayAdapter(
            activity,
            android.R.layout.simple_dropdown_item_1line,
            list
        )
        binding.editTime.setAdapter(adapter)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                back()
            }
            R.id.btn_submit -> {
                if (isValidation())
                    registerNowViewModel.registerEvent(
                        eventId.toString(),
                        selectedId.toString(),
                        binding.occupation.text.toString(),
                        getCurrentLanguage().language,
                        MultipartFormHelper.getMultiPartData(imagesURI!!)
                    )

                //
            }
        }
    }

    private fun isValidation(): Boolean {
        var isValid = true
        if (selectedId.isNullOrEmpty()) {
            isValid = false
            registerNowViewModel.showToast("Please select time")
        }
        if (binding.editOccupation.text.toString().isEmpty()) {
            registerNowViewModel.showToast("Please insert occupation")
            isValid = false
        }
        if (binding.editFullName.text.toString().isEmpty()) {
            isValid = false
            registerNowViewModel.showToast("Please insert fullname")

        }
        if (!binding.checkbox.isChecked) {
            isValid = false
            registerNowViewModel.showToast("Please check mark on Terms and Conditions")
        }
        if (imagesURI.isNullOrEmpty()) {
            registerNowViewModel.showToast("Please attach image")
            isValid = false
        }
        return isValid
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            val packageManager = activity.packageManager
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    val result = fileUtil.createImageFile(activity)
                    currentPhotoPath = result.second
                    result.first
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Timber.d("" + ex.toString())
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri? = activity.applicationContext?.let { it1 ->
                        FileProvider.getUriForFile(
                            it1,
                            Constants.PHOTO.FILE_PROVIDER,
                            it
                        )
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    val frag: Fragment = this
                    frag.startActivityForResult(
                        takePictureIntent,
                        Constants.PHOTO.REQUEST_IMAGE_CAPTURE
                    )
                }
            }
        }
    }

    private fun pickPicturesFromGallery() {
        try {
            UwMediaPicker
                .with(this)
                .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery)
                .setGridColumnCount(4)
                .setMaxSelectableMediaCount(1)
                .setLightStatusBar(true)
                .enableImageCompression(true)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setCompressionQuality(95)
                .launch { selectedMediaList ->
                    Timber.d("" + selectedMediaList?.get(0)?.mediaPath.toString())
                    var isExcludedFormatExist = false
                    selectedMediaList?.forEach {
                        it.mediaPath.apply {
                            val splitPath = this.split(".")
                            splitPath.apply {
                                if (splitPath.isNotEmpty()) {
                                    val spLength = splitPath.size - 1
                                    if (fileUtil.checkForImagesFormats(splitPath[spLength])) {
                                        val filename: String = it.mediaPath.substring(
                                            it.mediaPath.lastIndexOf(
                                                "/"
                                            ) + 1
                                        )
                                        imagesURI = it.mediaPath
                                        registerNowViewModel.getImage(
                                            filename,
                                            imagesURI!!,
                                            fileUtil,
                                            binding.tvUpload
                                        )
                                    } else {
                                        isExcludedFormatExist = true
                                    }
                                }
                            }
                        }
                    }

                    if (isExcludedFormatExist) {
//                                showAlert(Constants.CreatePost.FORMAT_IMAGES_MESSAGE)
                    }


                }
        } catch (e: Exception) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PDF_PICKER && resultCode == Activity.RESULT_OK) {
            val files: ArrayList<MediaFile> =
                data?.getParcelableArrayListExtra<MediaFile>(FilePickerActivity.MEDIA_FILES)!!
            registerNowViewModel.getFile(files, fileUtil, binding.tvUpload)
        } else
            if (requestCode == Constants.PHOTO.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                currentPhotoPath
                Timber.d(
                    "Image Size: " + fileUtil.calculateFileSize(
                        Uri.fromFile(File(currentPhotoPath)).toFile()
                    )
                )

            }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
