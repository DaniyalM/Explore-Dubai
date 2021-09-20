package com.app.dubaiculture.ui.postLogin.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentSurveyBinding
import com.app.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.app.dubaiculture.databinding.RowFillOutSurveyLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.faqs.FAQsItems
import com.app.dubaiculture.ui.postLogin.survey.adapter.SurveyAdapter
import com.app.dubaiculture.ui.postLogin.survey.viewmodel.SurveyViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyFragment : BaseFragment<FragmentSurveyBinding>() {

    private val surveyViewModel  : SurveyViewModel by viewModels()
    var surveyAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var eventId : String?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(surveyViewModel)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(surveyViewModel)
        arguments?.let {
            eventId =   it.getString("event_id")
        }
        surveyViewModel.getSurveyForm(eventId?:"0E49F5666F904C92B1BC41A13FD50B53",getCurrentLanguage().language)
        rvSetUp()
        subscribeObserver()
    }
   private fun subscribeObserver(){
        surveyViewModel.form.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                it.items.map {
                    surveyAdapter.add(
                        SurveyAdapter<RowFillOutSurveyLayoutBinding>(
                            itemForm = it,
                            resLayout = R.layout.row_fill_out_survey_layout,
                            context = requireContext(),
                            isArabic = isArabic()
                        )
                    )
                }
            }
        }
    }
    private fun rvSetUp(){
        binding.rvSurvey.apply {
            this.isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = surveyAdapter
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentSurveyBinding.inflate(inflater,container,false)
}