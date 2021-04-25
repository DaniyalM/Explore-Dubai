package com.app.dubaiculture.ui.postLogin.events.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Filter
import com.app.dubaiculture.data.repository.filter.models.SelectedItems
import com.app.dubaiculture.databinding.FragmentFilterBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment
import com.app.dubaiculture.ui.postLogin.events.filter.adapter.FilterCategoryItems
import com.app.dubaiculture.ui.postLogin.events.filter.viewmodel.FilterViewModel
import com.app.dubaiculture.ui.postLogin.events.viewmodel.EventViewModel
import com.app.dubaiculture.utils.Constants.Error.INTERNET_CONNECTION_ERROR
import com.app.dubaiculture.utils.DatePickerHelper
import com.app.dubaiculture.utils.toString
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_filter.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class FilterFragment : BaseBottomSheetFragment<FragmentFilterBinding>(), View.OnClickListener {
    private val eventViewModel: EventViewModel by activityViewModels()
    private val filterViewModel: FilterViewModel by viewModels()
    private var locationList = mutableListOf<Filter>()
    private var radioBtnList = mutableListOf<Filter>()
    private var categorysList = mutableListOf<Filter>()
    private var arrayList: ArrayList<String> = ArrayList()
    private var locationPos: Int = -1
    private var radioBtnID: String = ""
    private var radioBtnTitle: String = ""
    private var endDate: String? = ""
    private var startDate: String? = ""

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentFilterBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnFilter.setOnClickListener(this)
        binding.tvStartDate.setOnClickListener(this)
        binding.tvEndDate.setOnClickListener(this)
        binding.tvReset.setOnClickListener(this)
        subscribeUiEvents(eventViewModel)
        subscribeUiEvents(filterViewModel)
        subscribeToObservablesFilter()
        recyclerViewSetUp()


        binding.editSearch.setText(eventViewModel.keywordState.value.toString())
        binding.editLocation.setText(eventViewModel.locationState.value.toString())
        binding.tvStartDate.text = eventViewModel.dateFrmState.value.toString()
        binding.tvEndDate.text = eventViewModel.dateToState.value.toString()
        when {
            eventViewModel.radioBtnState.value.toString() == "Free" -> {
                binding.rbFree.isChecked = true
            }
            eventViewModel.radioBtnState.value.toString() == "Paid" -> {
                binding.rbPaid.isChecked = true
            }
            else -> {
                binding.rbFree.isChecked = true
            }
        }
        binding.radioGroupFilter.setOnCheckedChangeListener { radioGroup, id ->
            val selectedRadioButton =
                binding.radioGroupFilter.findViewById<View>(radioGroupFilter.checkedRadioButtonId) as RadioButton
            when (selectedRadioButton.text.toString()) {
                radioBtnList[0].title -> {
                    radioBtnID = radioBtnList[0].id!!
                    radioBtnTitle = radioBtnList[0].title!!
                    eventViewModel.radioBtnState.value = radioBtnTitle
                }
                radioBtnList[1].title -> {
                    radioBtnID = radioBtnList[1].id!!
                    radioBtnTitle = radioBtnList[1].title!!
                    eventViewModel.radioBtnState.value = radioBtnTitle
                }
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
                eventViewModel._filterDataList.value = selectedHeaderModel()
                dismiss()
            }
            R.id.tvReset -> {
                YoYo.with(Techniques.Bounce)
                    .duration(2000)
                    .playOn(binding.root)
                binding.editSearch.text!!.clear()
                binding.editLocation.text!!.clear()
                binding.tvStartDate.text = ""
                binding.tvEndDate.text = ""
                binding.rbFree.isChecked = true
                categorysList.clear()
                groupAdapter.clear()
                eventViewModel.keywordState.value = ""
                eventViewModel.locationState.value = ""
                eventViewModel.dateFrmState.value = ""
                eventViewModel.dateToState.value = ""
                eventViewModel.getDataFilterBtmSheet(getCurrentLanguage().language)
                eventViewModel._filterDataList.value = selectedHeaderModel()
            }
            R.id.tv_start_date -> {
                DatePickerHelper(binding.tvStartDate.text.toString(),
                    requireContext(),
                    object : DatePickerHelper.DatePickerInterface {
                        override fun onDateSelected(calendar: Calendar) {
                            val date: Date = calendar.time
                            val format = "yyyy-MM-dd"
                            val str = date.toString(format)
                            binding.tvStartDate.text = str
                            startDate = str
                            Timber.e("${startDate}")
                        }
                    }).showPicker()

            }
            R.id.tv_end_date -> {
                DatePickerHelper(binding.tvEndDate.text.toString(),
                    requireContext(),
                    object : DatePickerHelper.DatePickerInterface {
                        override fun onDateSelected(calendar: Calendar) {
                            val date: Date = calendar.time
                            val formater = "yyyy-MM-dd"
                            val str = date.toString(formater)
                            binding.tvEndDate.text = str
                            endDate = str
                            Timber.e("${endDate}")

                        }
                    }).showPicker()
            }
        }
    }


    private fun subscribeToObservablesFilter() {
        eventViewModel.filterList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    radioBtnTitle = it.value.radioGroupList!![0].title!!
                    radioBtnID = it.value.radioGroupList!![0].id!!
                    binding.rbFree.text = it.value.radioGroupList!![0].title
                    binding.rbPaid.text = it.value.radioGroupList!![1].title
                    it.value.locationList!!.forEach {
                        locationList.add(it)
                    }
                    it.value.radioGroupList!!.forEach {
                        radioBtnList.add(it)
                    }
                    it.value.categoryList!!.map {
                        categorysList.add(it)
                    }
                    it.value.locationList!!.forEach { filter ->
                        arrayList.add(filter.title!!)
                    }

                    categorysList.map { list ->
                        groupAdapter.add(FilterCategoryItems(list))
                    }
                    autoCompleteTextViewSetup()
                }
                is Result.Failure -> {
                    eventViewModel.showLoader(false)
                    eventViewModel.showToast(message = INTERNET_CONNECTION_ERROR)
                }
            }
        }
    }

    private fun autoCompleteTextViewSetup() {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(),
            R.layout.custom_list_items, R.id.text_view_list_item, arrayList)
        binding.editLocation.setAdapter(adapter)
        binding.editLocation.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                locationPos = position
            }
    }

    private fun selectedHeaderModel(): ArrayList<SelectedItems> {
        val list = ArrayList<SelectedItems>()
        list.clear()
        if (binding.editSearch.text.toString().isNotBlank()) {
            list.add(SelectedItems(keyword = binding.editSearch.text.toString()))
            eventViewModel.keywordState.value = binding.editSearch.text.toString()
        }
        if (binding.editLocation.text.toString().isNotBlank()) {
            list.add(SelectedItems(location = binding.editLocation.text.toString(),
                id = locationPos(binding.editLocation.text.toString())))
            eventViewModel.locationState.value = binding.editLocation.text.toString()

        }
        if (!binding.tvStartDate.text.toString().isNullOrEmpty()) {
            list.add(SelectedItems(dateFrom = binding.tvStartDate.text.toString()))
            eventViewModel.dateFrmState.value = binding.tvStartDate.text.toString()
        }
        if (! binding.tvEndDate.text.toString().isNullOrEmpty()) {
            list.add(SelectedItems(dateTo =  binding.tvEndDate.text.toString()))
            eventViewModel.dateToState.value = binding.tvEndDate.text.toString()
        }
        radioButtonState(list)
        eventViewModel.radioBtnState.value = radioBtnTitle
        categorysList.forEach {
            if (it.isSelected)
                list.add(SelectedItems(category = it.title, id = it.id)
                )
        }
        return list
    }

    private fun locationPos(text: String? = ""): String {
        var id: String? = ""
        val loc = locationList.filter {
            it.title == text
        }
        loc.forEach {
            id = it.id
        }
        return id ?: ""
    }

    fun radioButtonState(list : ArrayList<SelectedItems>){
        when {
            eventViewModel.radioBtnState.value.toString() == "Free" -> {
                binding.rbFree.isChecked = true
                radioBtnID = radioBtnList[0].id!!
                radioBtnTitle = radioBtnList[0].title!!
                list.add(SelectedItems(type = radioBtnTitle, id = radioBtnID))
            }
            eventViewModel.radioBtnState.value.toString() == "Paid" -> {
                binding.rbPaid.isChecked = true
                radioBtnID = radioBtnList[1].id!!
                radioBtnTitle = radioBtnList[1].title!!
                list.add(SelectedItems(type = radioBtnTitle, id = radioBtnID))
            }
            else -> {
                binding.rbFree.isChecked = true
                radioBtnID = radioBtnList[0].id!!
                radioBtnTitle = radioBtnList[0].title!!
                list.add(SelectedItems(type = radioBtnTitle, id = radioBtnID))
            }
        }
    }
}