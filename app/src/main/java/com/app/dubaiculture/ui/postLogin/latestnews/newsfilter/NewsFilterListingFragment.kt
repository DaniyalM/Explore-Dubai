package com.app.dubaiculture.ui.postLogin.latestnews.newsfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.databinding.EventSearchToolbarBinding
import com.app.dubaiculture.databinding.FragmentNewsFilterListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsPagingAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsClickListener
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.adapters.NewFilterListAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewFilterListingViewModel
import com.app.dubaiculture.ui.postLogin.latestnews.newsfilter.viewmodels.NewsSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFilterListingFragment : BaseFragment<FragmentNewsFilterListingBinding>() ,View.OnClickListener{

    private val newsFilterViewModel: NewsSharedViewModel by activityViewModels()
    private val newsFilterListingViewModel: NewFilterListingViewModel by viewModels()
    private lateinit var newsListAdapter: NewFilterListAdapter
    lateinit var eventSearchToolbarBinding: EventSearchToolbarBinding

    lateinit var filterBottomSheet:NewsFilterBottomSheet

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentNewsFilterListingBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventSearchToolbarBinding=binding.eventSearchToolbar
        eventSearchToolbarBinding.editSearchEvents.isEnabled=false
        eventSearchToolbarBinding.editSearchEvents.setOnClickListener(this)
        eventSearchToolbarBinding.materialCardView.setOnClickListener(this)
        eventSearchToolbarBinding.imgFilter.setOnClickListener(this)
        filterBottomSheet=NewsFilterBottomSheet()
        subscribeUiEvents(newsFilterViewModel)
        showBottomSheet(filterBottomSheet)
        rvSetup()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeToObservables()
    }

    private fun subscribeToObservables(){
        newsFilterViewModel.filter.observe(viewLifecycleOwner){
            it?.getContentIfNotHandled()?.let {
                eventSearchToolbarBinding.editSearchEvents.hint=it.keyword

                newsFilterListingViewModel.getFilterNews(it)
            }
        }

        newsFilterListingViewModel.news.observe(viewLifecycleOwner){
            newsListAdapter.submitList(it)
        }
    }

    private fun rvSetup(){
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            newsListAdapter= NewFilterListAdapter(object :NewsClickListener{
                override fun rowClickListener(news: LatestNews) {

                }

                override fun rowClickListener(news: LatestNews, position: Int) {

                }
            })

            adapter=newsListAdapter
        }

    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_filter->showBottomSheet(filterBottomSheet)
            R.id.materialCardView->showBottomSheet(filterBottomSheet)
            R.id.editSearchEvents->showBottomSheet(filterBottomSheet)
            R.id.back->back()
        }
    }

}