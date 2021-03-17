package com.app.dubaiculture.ui.postLogin.events.filter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.filter.Categories
import com.app.dubaiculture.data.repository.filter.models.Filter
import com.app.dubaiculture.databinding.FragmentFilterBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.events.filter.adapter.FilterCategoryItems
import com.app.dubaiculture.ui.postLogin.events.filter.viewmodel.FilterViewModel
import com.app.dubaiculture.utils.DatePickerHelper
import com.app.dubaiculture.utils.toString
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class FilterFragment : BaseBottomSheetFragment<FragmentFilterBinding>(), View.OnClickListener {
    private var mListener: ItemClickListener? = null
    private val filterViewModel: FilterViewModel by viewModels()
    private var filterList = mutableListOf<Categories>()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ItemClickListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement ItemClickListener")
        }
    }
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentFilterBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnFilter.setOnClickListener(this)
        binding.tvStartDate.setOnClickListener(this)
        binding.tvEndDate.setOnClickListener(this)

        recyclerViewSetUp()

        filterViewModel.categories.observe(viewLifecycleOwner) {
            filterList = it as MutableList<Categories>
            it.map {
                groupAdapter.add(FilterCategoryItems(it))
            }
        }


    }
    private fun recyclerViewSetUp() {
        binding.rvCategories.apply {
            val layoutManager = FlexboxLayoutManager(context)
            layoutManager.flexDirection = FlexDirection.ROW
            layoutManager.alignItems = AlignItems.STRETCH
            setLayoutManager(layoutManager)
            adapter = groupAdapter
        }
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_filter -> {
                val createTestData = createTestData()
                filterViewModel._filterDta.value = createTestData
                mListener!!.onItemClick("hello")
                Log.e("Model=>", createTestData.size.toString())
                dismiss()
            }
            R.id.tv_start_date->{
                DatePickerHelper(binding.tvStartDate.text.toString(),requireContext(), object : DatePickerHelper.DatePickerInterface{
                    override fun onDateSelected(calendar: Calendar) {
                        val date : Date = calendar.time
                        val format = "dd/MM/yy"
                        val str = date.toString(format)
                        binding.tvStartDate.text = str
                    }
                }).showPicker()
            }
            R.id.tv_end_date->{
                DatePickerHelper(binding.tvEndDate.text.toString(),requireContext(), object : DatePickerHelper.DatePickerInterface{
                    override fun onDateSelected(calendar: Calendar) {
                        val date : Date = calendar.time
                        val formater = "dd/MM/yy"
                        val str = date.toString(formater)
                        binding.tvEndDate.text = str
                    }
                }).showPicker()
            }
        }
    }
    private fun createTestData(): ArrayList<Filter> = mutableListOf<Filter>().apply {
        add(
            Filter(
                userID = "1",
                eventKeyword = "Near Event",
                location = "Al bashra",
                fromDate = "03/10/21",
                toDate = "03/16/21",
                type = "Free",
                culture = "en",
                category = filterTestCategory() as ArrayList<String>
            )
        )
    } as ArrayList<Filter>
    private fun filterTestCategory(): ArrayList<Categories> = mutableListOf<Categories>().apply {
        filterList.forEach {
            if (it.isSelected)
                add(
                    Categories(
                        title = it.title,
                        isSelected = it.isSelected
                    )
                )
        }
    } as ArrayList<Categories>
    interface ItemClickListener {
        fun onItemClick(item: String?)
    }
}