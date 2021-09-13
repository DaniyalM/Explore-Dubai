package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.databinding.EventSearchToolbarBinding
import com.app.dubaiculture.databinding.FragmentNewsFilterListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsClickListener
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters.NewFilterListAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewFilterListingViewModel
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewsSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFilterListingFragment : BaseFragment<FragmentNewsFilterListingBinding>(),
    View.OnClickListener {

    private lateinit var observer: RecyclerView.AdapterDataObserver
    private val newsFilterViewModel: NewsSharedViewModel by activityViewModels()
    private val newsFilterListingViewModel: NewFilterListingViewModel by viewModels()
    private lateinit var newsListAdapter: NewFilterListAdapter



    //    private lateinit var searchFilterSelectedAdapter: SearchFilterSelectedAdapter
    lateinit var eventSearchToolbarBinding: EventSearchToolbarBinding

//    private var selectedItems:MutableList<String> = mutableListOf()
//    private var selectedFilters:MutableList<Filter> = mutableListOf()

    lateinit var filterBottomSheet: NewsFilterBottomSheet

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsFilterListingBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(newsFilterListingViewModel)

        eventSearchToolbarBinding = binding.eventSearchToolbar
        eventSearchToolbarBinding.viewmodel = newsFilterViewModel
        eventSearchToolbarBinding.imgFilter.setOnClickListener(this)
        eventSearchToolbarBinding.imgSearch.setOnClickListener(this)
        filterBottomSheet = NewsFilterBottomSheet()
        showBottomSheet(filterBottomSheet)
        rvSetup()
        binding.eventSearchToolbar.back.setOnClickListener {
            back()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun subscribeToObservables() {


        newsFilterViewModel.filter.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                newsFilterListingViewModel.getFilterNews(it)

            }
        }



        newsFilterListingViewModel.news.observe(viewLifecycleOwner) {


            newsListAdapter.submitList(it)
        }
    }

    private fun rvSetup() {
//        binding.rvSelections.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//            searchFilterSelectedAdapter =
//                SearchFilterSelectedAdapter(object : SearchFilterSelectedAdapter.RemoveHeaderItem{
//                    override fun onItemRemove(pos: Int, list: List<String>) {
//                    }
//                })
//
//            adapter=searchFilterSelectedAdapter
//        }
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
            R.id.img_search ->  newsFilterViewModel.updateFilter(Filter(keyword = eventSearchToolbarBinding.editSearchEvents.text.toString().trim()))
            R.id.materialCardView -> showBottomSheet(filterBottomSheet)
            R.id.editSearchEvents -> showBottomSheet(filterBottomSheet)
            R.id.back -> back()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsListAdapter.unregisterAdapterDataObserver(observer)
    }


}

//                selectedFilters.add(it)
//                selectedFilters.forEach {
//                    if (it.dateFrom.isNotEmpty()){
//                        if(!selectedItems.contains(it.dateFrom)){
//                            selectedItems.add(it.dateFrom)
//                        }
//                    }
//                    if (it.dateTo.isNotEmpty()){
//                        if(!selectedItems.contains(it.dateTo)){
//                            selectedItems.add(it.dateTo)
//                        }
//                    }
//                    if (it.tags.isNotEmpty()){
//                        it.tags.forEach {
//                            if(!selectedItems.contains(it)){
//                                selectedItems.add(it)
//                            }
//                        }
//                    }
//
//                }
//                searchFilterSelectedAdapter.selectedItems=selectedItems
