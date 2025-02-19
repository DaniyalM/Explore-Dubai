package com.dubaiculture.ui.postLogin.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.work.impl.background.systemjob.SystemJobService
import com.dubaiculture.R
import com.dubaiculture.data.repository.survey.request.Form
import com.dubaiculture.databinding.FragmentSurveyBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.survey.SurveyFieldUtils.createButtonContainer
import com.dubaiculture.ui.postLogin.survey.SurveyFieldUtils.createCardForEditText
import com.dubaiculture.ui.postLogin.survey.SurveyFieldUtils.createCardForRadioButtons
import com.dubaiculture.ui.postLogin.survey.SurveyFieldUtils.createCardForRatingField
import com.dubaiculture.ui.postLogin.survey.viewmodel.SurveyViewModel
import com.idlestar.ratingstar.RatingStarView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyFragment : BaseFragment<FragmentSurveyBinding>() {

    private val surveyViewModel: SurveyViewModel by viewModels()
    var surveyAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var eventId: String? = null
    private lateinit var formSubmit: Form

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(surveyViewModel)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(surveyViewModel)
        lottieAnimationRTL(binding.animationView)

        arguments?.let {
            eventId = it.getString("event_id")
        }
        surveyViewModel.getSurveyForm(
            eventId ?: "485EA0E3A9934318A1047808B235AFF5",
            getCurrentLanguage().language
        )
        subscribeObserver()
        binding.imageView11.setOnClickListener {
            back()
        }

    }


    private fun subscribeObserver() {
        surveyViewModel.formSubmit.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                formSubmit = it
            }
        }
        surveyViewModel.form.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { form ->
                surveyViewModel.updateForm(form)
                binding.fillOutSurvey.text = form.title
                binding.header.text = form.subtitle
                val inflater = activity.getSystemService(SystemJobService.LAYOUT_INFLATER_SERVICE)
                        as LayoutInflater

                val count = form.items.size
                form.items.forEach { item ->
                    when (FieldType.fromName(item.input)) {
                        FieldType.YesNo -> {
                            createCardForRadioButtons(
                                inflater,
                                binding.fieldContainer,
                                item,
                                count,
                                isArabic()
                            )
                        }
                        FieldType.Rating -> {
                            createCardForRatingField(
                                inflater,
                                binding.fieldContainer,
                                item,
                                count,
                                isArabic()
                            )
                        }
                        FieldType.Textbox -> {
                            createCardForEditText(
                                inflater,
                                binding.fieldContainer,
                                item,
                                count,
                                isArabic()
                            )
                        }
                    }
                }
                createButtonContainer(
                    inflater,
                    binding.fieldContainer
                ) {
                    var isValid = true
                    formSubmit.items.forEach {
                        val view = binding.fieldContainer.findViewById<View>(it.index)
                        if (FieldType.fromName(it.input) == FieldType.Textbox) {
                            val value = (view as EditText).text.toString()
                            if (value.isNotEmpty()){
                                surveyViewModel.updateFormItem(formSubmit, it.copy(answer = value,isValid = true))
                            }
                        }
                        if (FieldType.fromName(it.input) == FieldType.Rating) {
                            val value = (view as RatingStarView).rating.toString()
                            if (value.isNotEmpty()){
                                surveyViewModel.updateFormItem(formSubmit, it.copy(answer = value,isValid = true))
                            }
                        }
                        if (FieldType.fromName(it.input) == FieldType.YesNo) {
                            val group = (view as RadioGroup)
                            // find the radiobutton by returned id
                            val radioButton =
                                group.findViewById<RadioButton>(group.checkedRadioButtonId)
                            if (group.checkedRadioButtonId != -1 || radioButton != null) {
                                val value = radioButton.text.toString()
                                if (value.isNotEmpty()){
                                    surveyViewModel.updateFormItem(formSubmit, it.copy(answer = value,isValid = true))
                                }
                            }
                        }
                        if (!it.isValid){
                            isValid=false
                        }

                    }

                    if (isValid){
                        surveyViewModel.postSurvey(
                            form = formSubmit.copy(
                                itemId = eventId ?: "485EA0E3A9934318A1047808B235AFF5",
                                culture = getCurrentLanguage().language
                            )
                        ) {
                            showToast(resources.getString(R.string.survey_submitted))
                            back()
                        }
                    }else {
                        showToast(resources.getString(R.string.please_recheck_your_evaluation))
                    }

                }
            }
        }


    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSurveyBinding.inflate(inflater, container, false)
}