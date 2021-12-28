package com.dubaiculture.ui.postLogin.eservices

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
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createDateField
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createDropDown
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createEditText
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createFileField
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createImageField
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createRadioButton
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createTextView
import com.dubaiculture.ui.postLogin.eservices.FieldUtils.createTimeField
import com.dubaiculture.ui.postLogin.eservices.viewmodels.EServiceViewModel
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class EServiceFragment : BaseFragment<FragmentEserviceBinding>() {
    private val eServiceViewModel: EServiceViewModel by viewModels()

    private val FILE_SELECTION_CODE = 121

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEserviceBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(eServiceViewModel)
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
            val id = it.id
            val view = binding.fieldContainer.findViewById<View>(id)
            if (ValueType.isInputField(it.valueType)) {
                val value = (view as EditText).text.toString()
                eServiceViewModel.addField(it, value)
            }
        }
        eServiceViewModel.submitForm()
    }

    private fun subscribeToObservable() {
        eServiceViewModel.fieldValues.observe(viewLifecycleOwner) {
            initializeFields(it)
        }
    }

    private fun initializeFields(fieldValues: List<GetFieldValueItem>) {
        val inflater =
            activity.getSystemService(SystemJobService.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        fieldValues.forEach {
            val fieldType =
                FieldType.fromName(it.fieldType)// might be type = message, so don't render
            val valueType =
                ValueType.fromName(it.valueType)// might be type = button, so don't render
            if (fieldType == null || valueType == null)
                return@forEach

            addViewToViewGroup(
                createTextView(
                    isArabic(),
                    inflater,
                    binding.fieldContainer,
                    it
                )
            )
            when (valueType?.id ?: -1) {
                ValueType.INPUT_TEXT.id -> {
                    addViewToViewGroup(
                        createEditText(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        )
                    )
                }
                ValueType.INPUT_NUMBER.id -> {
                    addViewToViewGroup(
                        createEditText(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        )
                    )
                }
                ValueType.INPUT_TEXT_MULTILINE.id -> {
                    addViewToViewGroup(
                        createEditText(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        )
                    )
                }
                ValueType.DATE.id -> {
                    addViewToViewGroup(
                        createDateField(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        ) {
//                                    showToast(it)
                        }
                    )
                }
                ValueType.TIME.id -> {
                    addViewToViewGroup(
                        createTimeField(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            childFragmentManager,
                            it
                        ) {
//                                    showToast(it)
                        }
                    )
                }
                ValueType.IMAGE.id -> {
                    addViewToViewGroup(
                        createImageField(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        )
                    )
                }
                ValueType.FILE.id -> {
                    addViewToViewGroup(
                        createFileField(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it,
                            callback = {
                                eServiceViewModel.selectedView = it
                                showFilePicker()
                            }
                        )
                    )
                }
                ValueType.DROP_DOWN.id -> {
                    addViewToViewGroup(
                        createDropDown(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        )
                    )
                }
                ValueType.RADIO_BUTTON.id -> {
                    addViewToViewGroup(
                        createRadioButton(
                            isArabic(),
                            inflater,
                            binding.fieldContainer,
                            it
                        ) { selectedValue ->
                            eServiceViewModel.addField(it, selectedValue)
                        }
                    )
                }
            }
        }
    }


    private fun addViewToViewGroup(view: View) {
        binding.fieldContainer.addView(view)
    }

    private fun showFilePicker() {
        val intent = Intent(activity, FilePickerActivity::class.java)
        intent.putExtra(
            FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowFiles(true)
                .setShowAudios(false)
                .setShowVideos(false)
                .setMaxSelection(1)
                .setSuffixes("pdf")
                .setSkipZeroSizeFiles(true)
                .build()
        )
        startActivityForResult(intent, FILE_SELECTION_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_SELECTION_CODE && resultCode == Activity.RESULT_OK) {
            val files: ArrayList<MediaFile>? =
                data?.getParcelableArrayListExtra<MediaFile>(FilePickerActivity.MEDIA_FILES)
            files?.let {
                val name = files[0].name
                val path = files[0].uri.path
                Timber.e(name)
                Timber.e(path)
                setFileDetails(files[0])
            }
        }
        eServiceViewModel.selectedView = null

        super.onActivityResult(requestCode, resultCode, data)

    }

    private fun setFileDetails(mediaFile: MediaFile) {
        eServiceViewModel.selectedView?.let {
            binding.fieldContainer.findViewById<EditText>(it.id).setText(mediaFile.name)
            eServiceViewModel.addField(it, mediaFile.path ?: "")
        }

    }
}