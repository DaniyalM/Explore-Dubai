package com.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dubaiculture.R
import com.dubaiculture.data.repository.news.local.NewsTags
import com.dubaiculture.databinding.FragmentBottomSheetNewsFilterBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsTagsClickListener
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters.NewsTagsListAdapter
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewsFilterSheetViewModel
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewsSharedViewModel
import com.dubaiculture.utils.AppConfigUtils.getCurrentDateTime
import com.dubaiculture.utils.Constants.NavBundles.SHEET_STATE
import com.dubaiculture.utils.DatePickerHelper
import com.dubaiculture.utils.toString
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewsFilterBottomSheet : BaseBottomSheetFragment<FragmentBottomSheetNewsFilterBinding>(),
    View.OnClickListener {
    private val newsFilterViewModel: NewsSharedViewModel by activityViewModels()
    private val newsFilterSheetViewModel: NewsFilterSheetViewModel by viewModels()
    private var startDateObj: Date? = null
    private lateinit var newsTagsListAdapter: NewsTagsListAdapter

    private var filter: Filter = Filter()

    private fun recyclerViewSetUp() {
        binding.rvCategories.apply {
            val list: MutableList<String> = mutableListOf()
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.alignItems = AlignItems.STRETCH
            setLayoutManager(layoutManager)
            newsTagsListAdapter = NewsTagsListAdapter(object : NewsTagsClickListener {
                override fun onTagSelectListner(newsTags: NewsTags) {
                    if (!filter.tags.contains(newsTags.tag_id) && newsTags.isSelected) {
                        list.add(newsTags.tag_id)
                        filter = filter.copy(tags = list)
                    } else {
                        list.remove(newsTags.tag_id)
                        filter = filter.copy(tags = list)
                    }

                }
            })
            adapter = newsTagsListAdapter
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentBottomSheetNewsFilterBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            filter = it.getParcelable(SHEET_STATE)!!
        }

        subscribeUiEvents(newsFilterSheetViewModel)
        recyclerViewSetUp()
        binding.viewmodel = newsFilterViewModel

        binding.tvStartDate.text = filter.dateFrom
        binding.tvEndDate.text = filter.dateTo
        binding.editSearch.hint = filter.keyword


        binding.btnFilter.setOnClickListener(this)
        binding.tvStartDate.setOnClickListener(this)
        binding.tvEndDate.setOnClickListener(this)
        binding.tvReset.setOnClickListener(this)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SHEET_STATE, filter)
    }


    private fun subscribeToObservables() {
        newsFilterViewModel.filter.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it.keyword.isNotEmpty())
                    binding.editSearch.setText(it.keyword)
            }
        }
//        newsFilterViewModel.updateFilter(filter)
        newsFilterViewModel.keyword.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                filter = filter.copy(keyword = it)
            }
        }
        newsFilterViewModel.dateFrom.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                filter = filter.copy(dateFrom = it)

                binding.tvStartDate.text = it
            }
        }
        newsFilterViewModel.dateTo.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                filter = filter.copy(dateTo = it)

                binding.tvEndDate.text = it
            }
        }
        newsFilterSheetViewModel.newsTags.observe(viewLifecycleOwner) {
            newsTagsListAdapter.submitList(it)

        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(newsFilterViewModel)
        subscribeToObservables()
    }

    override fun onClick(v: View?) {
        v?.apply {
            when (id) {
                R.id.btn_filter -> {
                    newsFilterViewModel.updateFilter(filter)
                    dismiss()

                }
                R.id.tv_start_date -> {
                    DatePickerHelper(
                        binding.tvStartDate.text.toString(),
                        requireContext(),
                        object : DatePickerHelper.DatePickerInterface {
                            override fun onDateSelected(calendar: Calendar) {
                                val date: Date = calendar.time
                                startDateObj = date
                                newsFilterViewModel.onStartDateSelection(date)
                                val format = "yyyy-MM-dd"
                                val str = date.toString(format)
                                binding.tvStartDate.text = str

                            }
                        }, fromDate = false
                    ).showPicker()
                }
                R.id.tv_end_date -> {
                    val startDate = startDateObj ?: getCurrentDateTime()

                    DatePickerHelper(
                        binding.tvEndDate.text.toString(),
                        requireContext(),
                        object : DatePickerHelper.DatePickerInterface {
                            override fun onDateSelected(calendar: Calendar) {
                                val date: Date = calendar.time

                                newsFilterViewModel.onEndDateSelection(date)
                                val format = "yyyy-MM-dd"
                                val str = date.toString(format)
                                binding.tvEndDate.text = str

                            }
                        }, minDate = startDate.time
                    ).showPicker()

                }
                R.id.tvReset -> {
                    filter = Filter()
                    binding.editSearch.setText("")
                    binding.tvEndDate.text = ""
                    binding.tvStartDate.text = ""
                    newsFilterSheetViewModel.updateTags()
                    newsFilterViewModel.updateFilter(filter)
//                    dismiss()
                }
            }
        }


    }
}