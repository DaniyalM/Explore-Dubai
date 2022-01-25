package com.dubaiculture.ui.postLogin.survey

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.repository.survey.request.Form
import com.dubaiculture.data.repository.survey.request.Items
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
            eventId ?: "485EA0E3A9934318A1047808B235AFF5",
            getCurrentLanguage().language
        )
        rvSetUp()
        subscribeObserver()
        binding.btnSubmit.setOnClickListener {
            surveyViewModel.postSurvey(form = formSubmit)
        }
    }


    public fun updateFormItem(input:String,items: Items){
        val data = formSubmit.items?:return
        data.map {
            if (it.id==items.id)
                return@map items.copy(answer = input)
            else
                return@map it
        }.let {
            formSubmit.items=it
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
                                    updateFormItem(input,it)
//                                    surveyViewModel.updateFormItem(
//                                        form,
//                                        items = it.copy(answer = input)
//                                    )
                                }
                            }
                        }
                    )

//                    when (FieldType.fromName(it.input)) {
//                        FieldType.YesNo -> {
//                           binding.customTextView1.text="1/ ${form.items.size} ${resources.getString(R.string.question)}"
//                            binding.tvQuestions.text=it.question
//                            binding.radioGroupYesNo.setOnCheckedChangeListener { group, checkedId ->
//                                when (checkedId) {
//                                    R.id.rbYes -> {
//                                        surveyViewModel.updateFormItem(
//                                            form,
//                                            items = it.copy(answer = "Yes")
//                                        )
//                                    }
//                                    else ->   surveyViewModel.updateFormItem(
//                                        form,
//                                        items = it.copy(answer = "No")
//                                    )
//                                }
//                            }
//
//                        }
//                        FieldType.Rating -> {
//                            binding.customTextView1.text="3/ ${form.items.size} ${resources.getString(R.string.question)}"
//                            binding.tvQuestions.text=it.question
//                            surveyViewModel.updateFormItem(
//                                form,
//                                items = it.copy(answer = binding.ratingStar.rating.toString())
//                            )
//
//                        }
//                        FieldType.Textbox -> {
//                            binding.customTextView1.text="2/ ${form.items.size} ${resources.getString(R.string.question)}"
//                            binding.tvQuestions.text=it.question
//                            binding.editComment.addTextChangedListener(object : TextWatcher {
//                                override fun beforeTextChanged(
//                                    s: CharSequence?,
//                                    start: Int,
//                                    count: Int,
//                                    after: Int
//                                ) {
//
//                                }
//
//                                override fun onTextChanged(
//                                    s: CharSequence?,
//                                    start: Int,
//                                    before: Int,
//                                    count: Int
//                                ) {
//                                    surveyViewModel.updateFormItem(
//                                        form,
//                                        items = it.copy(answer = s.toString().trim())
//                                    )
//                                }
//
//                                override fun afterTextChanged(s: Editable?) {
//
//                                }
//                            })
//                        }
//                    }


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