package com.app.dubaiculture.ui.postLogin.events.filter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.filter.models.FilterData
import com.app.dubaiculture.databinding.FragmentFilterBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.events.filter.adapter.FilterCategoryItems
import com.app.dubaiculture.ui.postLogin.events.filter.viewmodel.FilterViewModel
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
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
    private val eventViewModel: EventViewModel by activityViewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private var locationList = mutableListOf<Filter>()
    private var arrayList: ArrayList<String> = ArrayList()
    private var locationPos : Int= 0

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
        subscribeUiEvents(eventViewModel)
        subscribeUiEvents(filterViewModel)
        recyclerViewSetUp()
        subscribeToObservablesFilter()
        autoCompleteTextViewSetup()
//        filterViewModel.categories.observe(viewLifecycleOwner) {
//            filterList = it as MutableList<Categories>
//            it.map {
//                groupAdapter.add(FilterCategoryItems(it))
//            }
//        }


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
                filterViewModel._filterDataDta.value = createTestData
                mListener!!.onItemClick("hello")
                Log.e("Model=>", createTestData.size.toString())
                dismiss()
            }
            R.id.tv_start_date -> {
                DatePickerHelper(binding.tvStartDate.text.toString(),
                    requireContext(),
                    object : DatePickerHelper.DatePickerInterface {
                        override fun onDateSelected(calendar: Calendar) {
                            val date: Date = calendar.time
                            val format = "dd/MM/yy"
                            val str = date.toString(format)
                            binding.tvStartDate.text = str
                        }
                    }).showPicker()
            }
            R.id.tv_end_date -> {
                DatePickerHelper(binding.tvEndDate.text.toString(),
                    requireContext(),
                    object : DatePickerHelper.DatePickerInterface {
                        override fun onDateSelected(calendar: Calendar) {
                            val date: Date = calendar.time
//                            2021-02-01
                            val formater = "yyyy-MM-dd"
                            val str = date.toString(formater)
                            binding.tvEndDate.text = str
                        }
                    }).showPicker()
            }
        }
    }
    private fun createTestData(): ArrayList<FilterData> = mutableListOf<FilterData>().apply {
        add(
            FilterData(
                userID = "1",
                eventKeyword = binding.editSearch.text.toString(),
                location = locationList[locationPos].id,
                fromDate = binding.tvStartDate.text.toString(),
                toDate = binding.tvEndDate.text.toString(),
                type = "Free",
                culture = getCurrentLanguage().language,
                category = filterTestCategory() as ArrayList<String>
            )
        )
    } as ArrayList<FilterData>
    private fun filterTestCategory(): ArrayList<Filter> = mutableListOf<Filter>().apply {
       forEach {
            if (it.isSelected)
                add(
                    Filter(
                        id = it.id,
                        isSelected = it.isSelected
                    )
                )
        }
    } as ArrayList<Filter>




    interface ItemClickListener {
        fun onItemClick(item: String?)
    }

    fun subscribeToObservablesFilter(){
        eventViewModel.filterList.observe(viewLifecycleOwner){
            when(it){
                is Result.Success -> {

                    it.value.categoryList!!.map {
                        groupAdapter.add(FilterCategoryItems(it))
                    }
                    it.value.locationList!!.forEach { filter ->
                        arrayList.add(filter.title!!)
                    }

                }
                is Result.Failure -> {
                    eventViewModel.showLoader(false)
                }
            }
        }
    }

    private fun autoCompleteTextViewSetup(){
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
            R.layout.custom_list_items, R.id.text_view_list_item, arrayList)
        binding.editLocation.setAdapter(adapter)

        binding.editLocation.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                locationPos = position
            }
    }
}