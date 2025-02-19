package com.dubaiculture.ui.postLogin.eservices

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.work.impl.background.systemjob.SystemJobService
import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem
import com.dubaiculture.databinding.FragmentEserviceBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.eservices.enums.ValueType
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createCheckBox
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createDateFieldWithLabel
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createDropDownWithLabel
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createEditTextWithLabel
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createFileFieldWithLabel
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createImageFieldWithLabel
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createRadioButtonWithLabel
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createTextViewWithDescription
import com.dubaiculture.ui.postLogin.eservices.util.FieldUtils.createTimeFieldWithLabel
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import com.dubaiculture.ui.postLogin.events.detail.registernow.attachmentOptions.AttachmentOptionFragment
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.FileUtils
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EServiceFragment : BaseFragment<FragmentEserviceBinding>() {
    private val args: EServiceFragmentArgs by navArgs()
    private val eServiceViewModel: EServiceViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEserviceBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(eServiceViewModel)
        backArrowRTL(binding.include.back)
        binding.heading.text = args.formTitle
        binding.include.back.setOnClickListener {
            back()
        }
        subscribeToObservable()
        binding.submitButton.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        eServiceViewModel.fieldValues.value?.forEach {
            val view = binding.fieldContainer.findViewById<View>(it.id)
            if (ValueType.isInputField(it.valueType)) {
                val value = (view as EditText).text.toString()
                eServiceViewModel.addField(it.fieldName, value)
            }
        }
        eServiceViewModel.submitForm(isArabic())
    }

    private fun subscribeToObservable() {
        eServiceViewModel.fieldValues.observe(viewLifecycleOwner) {
            initializeFields(it)
        }
        eServiceViewModel.showField.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                FieldUtils.showField(binding.fieldContainer, it.first, it.second)
            }
        }
        eServiceViewModel.makeOptional.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                FieldUtils.makeFieldOptional(binding.fieldContainer, it.first, it.second)
            }
        }
    }

    private fun initializeFields(fieldValues: List<GetFieldValueItem>) {
        val inflater =
            activity.getSystemService(SystemJobService.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        fieldValues.forEach {
//            val fieldType =
//                FieldType.fromName(it.fieldType)// might be type = message, so don't render
            val valueType =
                ValueType.fromName(it.valueType)
                    ?: return@forEach// might be type = button, so don't render

            when (valueType.id) {
                ValueType.INPUT_TEXT.id -> {
                    createEditTextWithLabel(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        it
                    )
                }
                ValueType.INPUT_TEXT_MULTILINE.id -> {
                    createEditTextWithLabel(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        it
                    )
                }
                ValueType.DATE.id -> {
                    createDateFieldWithLabel(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        it,
                        eServiceViewModel.showFutureDates(it.fieldName),
                        eServiceViewModel.showPastDates(it)
                    ) { selectedValue ->
                        eServiceViewModel.addField(it.fieldName, selectedValue)
                    }
                }
                ValueType.TIME.id -> {
                    createTimeFieldWithLabel(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        childFragmentManager,
                        it
                    ) { selectedValue ->
                        eServiceViewModel.addField(it.fieldName, selectedValue)
                    }
                }
                ValueType.IMAGE.id -> {
                    createImageFieldWithLabel(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        it
                    )
                }
                ValueType.FILE.id -> {
                    createFileFieldWithLabel(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        it,
                        callback = {
                            eServiceViewModel.selectedView = it
                            showPickerOptions()
                        }
                    )
                }
                ValueType.DROP_DOWN.id -> {
                    createDropDownWithLabel(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        it
                    ) { selectedValue ->
                        eServiceViewModel.addField(it.fieldName, selectedValue)
                    }
                }
                ValueType.RADIO_BUTTON.id -> {
                    if (it.fieldValue.size > 1) {
                        createRadioButtonWithLabel(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        ) { selectedValue ->
                            eServiceViewModel.addField(it.fieldName, selectedValue)
                        }
                    } else {
                        createCheckBox(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        ) { isChecked, selectedValue ->
                            eServiceViewModel.addField(it.fieldName, selectedValue, isChecked)
                        }
                    }
                }
                ValueType.LABEL.id -> {
                    createTextViewWithDescription(
                        isArabic(),
                        inflater,
                        binding.fieldContainer,
                        it
                    )
                }
            }
        }
    }

    private fun showPickerOptions() {
        activity.runWithPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) {
            val attachmentOption = AttachmentOptionFragment(object :
                AttachmentOptionFragment.AttachmentOptionClickListener {
                override fun onOptionSelection(position: Int) {
                    showFilePicker(position)
                }
            })
            showBottomSheet(attachmentOption)
        }
    }

    private fun showFilePicker(position: Int) {
        //  1-image / 2-doc
        val intent = Intent(activity, FilePickerActivity::class.java)
        var conf = Configurations.Builder()
            .setCheckPermission(true)
            .setShowAudios(false)
            .setShowVideos(false)
            .setMaxSelection(1)
            .setSkipZeroSizeFiles(true)

        conf = conf.setShowImages(position == 1)
        conf = conf.setShowFiles(position == 2)

        if (position == 2) {
            conf = conf.setSuffixes(*Constants.ImagePicker.E_SERVICE_DOC_FORMATS)
        }

        intent.putExtra(
            FilePickerActivity.CONFIGS, conf.build()
        )
        startActivityForResult(intent, Constants.ImagePicker.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.ImagePicker.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val files: ArrayList<MediaFile>? =
                data?.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
            if (!files.isNullOrEmpty()) {
                setFileDetails(files[0])
            }
        }
        eServiceViewModel.selectedView = null

        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun setFileDetails(mediaFile: MediaFile) {
        eServiceViewModel.selectedView?.let {
            binding.fieldContainer.findViewById<EditText>(it.id).setText(mediaFile.name)
            val file = FileUtils().fileFromContentUri(requireContext(), mediaFile.uri)
            eServiceViewModel.addField(
                it.fieldName,
                file.absolutePath
            )
        }
    }
}