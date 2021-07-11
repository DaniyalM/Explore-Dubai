package com.app.dubaiculture.ui.postLogin.latestnews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
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
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.NEWS_ID
import com.app.dubaiculture.utils.Constants.NavBundles.NEWS_NAVIGATION
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatestNewsFragment : BaseFragment<FragmentLatestNewsBinding>(), View.OnClickListener {
    private val newsViewModel: NewsViewModel by viewModels()
    var newsListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    var backflagNavigation = false
    var latestNews: String? = null
    private var newsRequest: NewsRequest = NewsRequest(
            pageNumber = 0, pageSize = 6
    )
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            latestNews=it.getString(NEWS_ID)
        }

    }
    var contentLoadMore = true
    var newsVerticalAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentLatestNewsBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (backflagNavigation) {
            backflagNavigation = false
            latestNews=null
            navigateBack()
        }
        if (!latestNews.isNullOrEmpty()){
            backflagNavigation = true
            val bundle = bundleOf(NEWS_ID to latestNews)
            navigate(R.id.action_latestNewsFragment_to_newsDetailFragment,bundle)
        }

        subscribeUiEvents(newsViewModel)
        backArrowRTL(binding.imgClose)
        subscribeToObservables()
        initiateRequest()
        rvSetup()
        binding.imgClose.setOnClickListener {
            back()
        }
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
        if (newsListAdapter.itemCount > 0) {
            newsListAdapter.clear()
        }
        latestNews.forEach {
            newsListAdapter.add(
                    NewsItems<ItemsLatestNewsBinding>(
                            object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    val bundle = bundleOf(NEWS_ID to it.id)
                                    navigate(R.id.action_latestNewsFragment_to_newsDetailFragment, bundle)
                                }

                                override fun rowClickListener(position: Int, imageView: ImageView) {

                                }

                            }, latestNews = it, R.layout.items_latest_news, requireContext()
                    )
            )
        }
        binding.indicator.attachToRecyclerView(binding.rvHorizontalNews, PagerSnapHelper())
    }

    private fun addNews(latestNews: List<LatestNews>) {
        if (latestNews.isEmpty()) {
            newsRequest.pageNumber -= 1
        }
        if (newsVerticalAdapter.itemCount > 0) {
            newsVerticalAdapter.clear()
        }

        latestNews.forEach {
            newsVerticalAdapter.add(
                    NewsItems<ItemsNewsVerticalLayoutBinding>(
                            object : RowClickListener {
                                override fun rowClickListener(position: Int) {
                                    val bundle = bundleOf(NEWS_ID to it.id)
                                    navigate(R.id.action_latestNewsFragment_to_newsDetailFragment, bundle)
                                }

                                override fun rowClickListener(position: Int, imageView: ImageView) {

                                }
                            }, latestNews = it, R.layout.items_news_vertical_layout, requireContext()
                    )
            )
        }

    }


    private fun rvSetup() {
        binding.rvHorizontalNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newsListAdapter
            val pagerSnapHelper = PagerSnapHelper()
            this.onFlingListener = null
            pagerSnapHelper.attachToRecyclerView(this)
//            addItemDecoration(LinePagerIndicatorDecoration(requireContext()))
        }

        binding.rvVerticalNews.apply {
            var pastVisiblesItems: Int
            var visibleItemCount: Int
            var totalItemCount: Int
            this.isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsVerticalAdapter
            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) { //check for scroll down
                        (layoutManager as LinearLayoutManager).apply {
                            visibleItemCount = this.childCount
                            totalItemCount = this.itemCount
                            pastVisiblesItems = this.findFirstVisibleItemPosition()
                        }
                        if (contentLoadMore) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                contentLoadMore = false
                                newsRequest.pageNumber += 1
                                newsViewModel.showToast("Load More Triggered")
                                initiateRequest()
                                // Do pagination.. i.e. fetch new data
                            }
                        }
                    }
                }
            })
        }
//        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener(object : ViewTreeObserver.OnScrollChangedListener {
//            override fun onScrollChanged() {
//
//                val diff: Int = binding.nestedScrollView.getChildAt(binding.nestedScrollView.getChildCount() - 1).bottom - (binding.nestedScrollView.getHeight() + binding.nestedScrollView
//                        .getScrollY())
//                if(diff == 0)
//
//            }
//        })
//    }

    }
}
