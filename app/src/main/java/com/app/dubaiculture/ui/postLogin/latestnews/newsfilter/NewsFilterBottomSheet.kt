package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentBottomSheetNewsFilterBinding
import com.app.dubaiculture.databinding.FragmentFilterBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewsSharedViewModel
import com.app.dubaiculture.utils.DatePickerHelper
import com.app.dubaiculture.utils.toString
import timber.log.Timber
import java.util.*

class NewsFilterBottomSheet : BaseBottomSheetFragment<FragmentBottomSheetNewsFilterBinding>(), View.OnClickListener {
    private val newsFilterViewModel:NewsSharedViewModel by activityViewModels()
    private var startDateObj : Date?= null

    private var filter:Filter = Filter()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBottomSheetNewsFilterBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(newsFilterViewModel)
        binding.viewmodel=newsFilterViewModel

        binding.tvStartDate.text=filter.dateFrom
        binding.tvEndDate.text=filter.dateTo
        binding.editSearch.hint=filter.keyword


        binding.btnFilter.setOnClickListener(this)
        binding.tvStartDate.setOnClickListener(this)
        binding.tvEndDate.setOnClickListener(this)
        binding.tvReset.setOnClickListener(this)
        subscribeToObservables()
    }




    private fun subscribeToObservables(){
        newsFilterViewModel.updateFilter(filter)

        newsFilterViewModel.keyword.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let {
                newsFilterViewModel.updateFilter(filter.copy(keyword = it))
            }
        }
        newsFilterViewModel.dateFrom.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let {
                binding.tvStartDate.text=it
                newsFilterViewModel.updateFilter(filter.copy(dateFrom = it))
            }
        }
        newsFilterViewModel.dateTo.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let {
                binding.tvEndDate.text=it
                newsFilterViewModel.updateFilter(filter.copy(dateTo = it))


            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(newsFilterViewModel)
    }
    override fun onClick(v: View?) {
        v?.apply {
            when(id){
                R.id.btn_filter -> {
//                    newsFilterViewModel.displayFilters()
                }
                R.id.tv_start_date -> {
                    DatePickerHelper(binding.tvStartDate.text.toString(),
                        requireContext(),
                        object : DatePickerHelper.DatePickerInterface {
                            override fun onDateSelected(calendar: Calendar) {
                                val date: Date = calendar.time
                                newsFilterViewModel.onStartDateSelection(date)
                                val format = "yyyy-MM-dd"
                                val str = date.toString(format)
                                binding.tvStartDate.text = str

                            }
                        },fromDate = false).showPicker()
                }
                R.id.tv_end_date -> {
                    DatePickerHelper(binding.tvEndDate.text.toString(),
                        requireContext(),
                        object : DatePickerHelper.DatePickerInterface {
                            override fun onDateSelected(calendar: Calendar) {
                                val date: Date = calendar.time
                                newsFilterViewModel.onEndDateSelection(date)
                                val format = "yyyy-MM-dd"
                                val str = date.toString(format)
                                binding.tvEndDate.text = str

                            }
                        },minDate = startDateObj?.time).showPicker()

                }
                R.id.tvReset -> {

                }
            }
        }


    }
}