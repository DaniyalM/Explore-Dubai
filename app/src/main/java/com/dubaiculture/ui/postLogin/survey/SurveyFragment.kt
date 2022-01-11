package com.dubaiculture.ui.postLogin.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.survey.request.Form
import com.dubaiculture.databinding.FragmentSurveyBinding
import com.dubaiculture.databinding.RowFillOutSurveyLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.survey.adapter.SurveyAdapter
import com.dubaiculture.ui.postLogin.survey.viewmodel.SurveyViewModel
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
        arguments?.let {
            eventId = it.getString("event_id")
        }
        surveyViewModel.getSurveyForm(
            eventId ?: "0E49F5666F904C92B1BC41A13FD50B53",
            getCurrentLanguage().language
        )
        rvSetUp()
        subscribeObserver()
        binding.btnSubmit.setOnClickListener {
            surveyViewModel.postSurvey(form = formSubmit)
        }
    }

    private fun subscribeObserver() {
        surveyViewModel.form.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { form ->

                formSubmit = form
                surveyAdapter.clear()
                form.items.forEach {
                    surveyAdapter.add(
                        SurveyAdapter<RowFillOutSurveyLayoutBinding>(
                            itemForm = it,
                            resLayout = R.layout.row_fill_out_survey_layout,
                            context = requireContext(),
                            isArabic = isArabic()
                        ) { input, fieldType ->
                            when (fieldType) {
                                FieldType.YesNo -> {
                                    surveyViewModel.updateFormItem(
                                        form,
                                        items = it.copy(answer = input)
                                    )

                                }
                                FieldType.Rating -> {
                                    surveyViewModel.updateFormItem(
                                        form,
                                        items = it.copy(answer = input)
                                    )

                                }
                                FieldType.Textbox -> {
                                    surveyViewModel.updateFormItem(
                                        form,
                                        items = it.copy(answer = input)
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }


    }

    private fun rvSetUp() {
        binding.rvSurvey.apply {
            this.isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = surveyAdapter
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSurveyBinding.inflate(inflater, container, false)
}