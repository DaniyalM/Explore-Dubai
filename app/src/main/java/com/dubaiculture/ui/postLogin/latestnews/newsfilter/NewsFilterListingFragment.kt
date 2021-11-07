package com.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dubaiculture.R
import com.dubaiculture.data.repository.news.local.LatestNews
import com.dubaiculture.databinding.EventSearchToolbarBinding
import com.dubaiculture.databinding.FragmentNewsFilterListingBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsClickListener
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters.NewFilterListAdapter
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters.SelectedFiltersListAdapter
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewFilterListingViewModel
import com.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewsSharedViewModel
import com.dubaiculture.utils.hide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFilterListingFragment : BaseFragment<FragmentNewsFilterListingBinding>(),
    View.OnClickListener {

    private lateinit var observer: RecyclerView.AdapterDataObserver

//    private lateinit var observerSelected: RecyclerView.AdapterDataObserver
    private val newsFilterViewModel: NewsSharedViewModel by activityViewModels()
    private val newsFilterListingViewModel: NewFilterListingViewModel by viewModels()
    private lateinit var newsListAdapter: NewFilterListAdapter
    private lateinit var searchFilterSelectedAdapter: SelectedFiltersListAdapter
    lateinit var eventSearchToolbarBinding: EventSearchToolbarBinding

    private var filter: Filter = Filter()
    lateinit var filterBottomSheet: NewsFilterBottomSheet

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsFilterListingBinding.inflate(inflater, container, false)

    override fun onDetach() {
        super.onDetach()
        newsFilterViewModel.clearFilters()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(newsFilterListingViewModel)
        filterBottomSheet = NewsFilterBottomSheet()
        binding.selectedViews.hide()
        eventSearchToolbarBinding = binding.eventSearchToolbar
        backArrowRTL(eventSearchToolbarBinding.back)
        eventSearchToolbarBinding.viewmodel = newsFilterViewModel
        eventSearchToolbarBinding.imgFilter.setOnClickListener(this)
        eventSearchToolbarBinding.imgSearch.setOnClickListener(this)
        rvSetup()
        eventSearchToolbarBinding.back.setOnClickListener {
            back()
        }
        binding.tvClearAll.setOnClickListener {
            newsFilterViewModel.clearFilters()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }


    private fun subscribeToObservables() {

        newsFilterViewModel.selectedItems.observe(viewLifecycleOwner) {
            binding.eventSearchToolbar.tvBadge.text = it.size.toString()
            searchFilterSelectedAdapter.submitList(it)

            val tags = mutableListOf<String>()
            it.forEach { filterSelected ->
                if (filterSelected.isKeyword) {
                    filter = filter.copy(keyword = filterSelected.title)
                } else filter = filter.copy(keyword = "")
                if (filterSelected.isDateTo) {
                    filter = filter.copy(dateTo = filterSelected.title)
                } else filter = filter.copy(dateTo = "")
                if (filterSelected.isDateFrom) {
                    filter = filter.copy(dateFrom = filterSelected.title)
                } else filter = filter.copy(dateFrom = "")
                if (filterSelected.isTag) {
                    tags.add(filterSelected.title)
                } else filter = filter.copy(tags = mutableListOf())
            }
            if (tags.isNotEmpty()) {
                filter = filter.copy(tags = tags)
            }
            if (it.isEmpty())
                filter = Filter()

            newsFilterListingViewModel.getFilterNews(filter)

        }


        newsFilterViewModel.filter.observe(viewLifecycleOwner) {

            it?.getContentIfNotHandled()?.let {
                filter = filter.copy(
                    tags = it.tags,
                    keyword = it.keyword,
                    dateFrom = it.dateFrom,
                    dateTo = it.dateTo
                )
                initiateSelectedItems()

            }
        }



        newsFilterListingViewModel.news.observe(viewLifecycleOwner) {
            newsListAdapter.submitList(it)
        }
    }

    private fun rvSetup() {
        binding.rvSelections.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            searchFilterSelectedAdapter = SelectedFiltersListAdapter(object :
                SelectedFiltersListAdapter.RemoveHeaderItem {
                override fun onItemRemove(item: SelectedFilter) {
                    newsFilterViewModel.removeFilter(item)
                }
            })
            adapter = searchFilterSelectedAdapter
//            observerSelected = object : RecyclerView.AdapterDataObserver() {
//                override fun onChanged() {
//                    super.onChanged()
//                    checkEmpty()
//                }
//
//                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                    super.onItemRangeInserted(positionStart, itemCount)
//                    checkEmpty()
//                }
//
//                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
//                    super.onItemRangeRemoved(positionStart, itemCount)
//                    checkEmpty()
//                }
//
//                fun checkEmpty() {
//                    binding.selectedViews.visibility =
//                        (if (searchFilterSelectedAdapter.itemCount > 0) View.VISIBLE else View.GONE)
//                }
//            }
//            searchFilterSelectedAdapter.registerAdapterDataObserver(observerSelected)

        }
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            newsListAdapter = NewFilterListAdapter(object : NewsClickListener {
                override fun rowClickListener(news: LatestNews) {
                    navigateByDirections(
                        NewsFilterListingFragmentDirections.actionNewsFilterListingFragmentToNewsDetailNavigation(
                            news.id!!
                        )
                    )
                }

                override fun rowClickListener(news: LatestNews, position: Int) {

                }
            })

            adapter = newsListAdapter

            observer = object :
                RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    checkEmpty()
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    checkEmpty()
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart, itemCount)
                    checkEmpty()
                }

                fun checkEmpty() {
                    binding.noDataPlaceHolder.visibility =
                        (if (newsListAdapter.itemCount == 0) View.VISIBLE else View.GONE)
                }
            }
            newsListAdapter.registerAdapterDataObserver(observer)
        }

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_filter -> showBottomSheet(filterBottomSheet)
            R.id.img_search -> newsFilterViewModel.updateFilter(
                Filter(
                    keyword = eventSearchToolbarBinding.editSearchEvents.text.toString().trim()
                )
            )
            R.id.materialCardView -> showBottomSheet(filterBottomSheet)
            R.id.editSearchEvents -> showBottomSheet(filterBottomSheet)
            R.id.back -> back()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsListAdapter.unregisterAdapterDataObserver(observer)
//        searchFilterSelectedAdapter.unregisterAdapterDataObserver(observerSelected)

    }

    private fun initiateSelectedItems() {
        val _selectedItemsTemp: MutableList<SelectedFilter> = mutableListOf()

        filter.apply {
            if (keyword.isNotEmpty()) {
                binding.eventSearchToolbar.editSearchEvents.setText(keyword)
                _selectedItemsTemp.add(
                    SelectedFilter(
                        title = keyword,
                        isKeyword = true
                    )
                )
            }
            if (dateFrom.isNotEmpty()) {
                _selectedItemsTemp.add(
                    SelectedFilter(
                        title = dateFrom,
                        isDateFrom = true
                    )
                )

            }
            if (dateTo.isNotEmpty()) {
                _selectedItemsTemp.add(
                    SelectedFilter(
                        title = dateTo,
                        isDateFrom = true
                    )
                )

            }
            if (tags.isNotEmpty()) {
                tags.forEach {
                    _selectedItemsTemp.add(
                        SelectedFilter(
                            title = it,
                            isTag = true
                        )
                    )

                }
            }
            newsFilterViewModel.updateSelectedItem(_selectedItemsTemp)
        }


    }

}


