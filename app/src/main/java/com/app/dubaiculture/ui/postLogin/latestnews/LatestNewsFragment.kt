package com.app.dubaiculture.ui.postLogin.latestnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.remote.request.NewsRequest
import com.app.dubaiculture.databinding.FragmentLatestNewsBinding
import com.app.dubaiculture.databinding.ItemsLatestNewsBinding
import com.app.dubaiculture.databinding.ItemsNewsVerticalLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsItems
import com.app.dubaiculture.ui.postLogin.latestnews.viewmodel.NewsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatestNewsFragment : BaseFragment<FragmentLatestNewsBinding>(), View.OnClickListener {
    private val newsViewModel: NewsViewModel by viewModels()
    private var newsRequest: NewsRequest = NewsRequest(
            0,3
    )
    var newsVerticalAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentLatestNewsBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(newsViewModel)
        subscribeToObservables()
        initiateRequest()
        rvSetup()
    }


    private fun initiateRequest() {


        binding.swipeRefresh.post {
            binding.swipeRefresh.isRefreshing = true
            newsViewModel.getNews(newsRequest)
        }
        binding.swipeRefresh.apply {
            setColorSchemeResources(
                    R.color.colorPrimary,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_blue_dark
            )
            setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                newsViewModel.getNews(newsRequest)
            }
        }
    }

    private fun subscribeToObservables() {
        newsViewModel.news.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            it?.getContentIfNotHandled()?.let {
                addLatestNews(it.latestNews)
                addNews(it.news)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    private fun addLatestNews(latestNews: List<LatestNews>) {
        if (groupAdapter.itemCount > 0) {
            groupAdapter.clear()
        }
        latestNews.forEach {
            groupAdapter.add(
                    NewsItems<ItemsLatestNewsBinding>(
                            object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    navigate(R.id.action_latestNewsFragment_to_newsDetailFragment)

                                }

                            }, latestNews = it, R.layout.items_latest_news, requireContext()
                    )
            )

        }

        binding.indicator.attachToRecyclerView( binding.rvHorizontalNews,PagerSnapHelper())

    }

    private fun addNews(latestNews: List<LatestNews>) {
        if (newsVerticalAdapter.itemCount > 0) {
            newsVerticalAdapter.clear()
        }

        latestNews.forEach {
            newsVerticalAdapter.add(
                    NewsItems<ItemsNewsVerticalLayoutBinding>(
                            object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    navigate(R.id.action_latestNewsFragment_to_newsDetailFragment)

                                }

                            }, latestNews = it, R.layout.items_news_vertical_layout, requireContext()
                    )
            )
        }

    }


    private fun rvSetup() {
        binding.rvHorizontalNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
            val pagerSnapHelper = PagerSnapHelper()
            this.onFlingListener = null
            pagerSnapHelper.attachToRecyclerView(this)

        }

        binding.rvVerticalNews.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsVerticalAdapter

        }
    }


}
