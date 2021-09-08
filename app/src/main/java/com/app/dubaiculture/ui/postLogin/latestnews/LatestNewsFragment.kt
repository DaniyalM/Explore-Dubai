package com.app.dubaiculture.ui.postLogin.latestnews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.LatestNewsListAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsItems
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsPagingAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.clicklisteners.NewsClickListener
import com.app.dubaiculture.ui.postLogin.latestnews.viewmodel.NewsViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.NEWS_ID
import com.app.dubaiculture.utils.Constants.NavBundles.NEWS_NAVIGATION
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LatestNewsFragment : BaseFragment<FragmentLatestNewsBinding>(), View.OnClickListener {
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var latestNewsListAdapter: LatestNewsListAdapter
    private lateinit var newsListAdapter: NewsPagingAdapter
    var backflagNavigation = false
    var latestNews: String? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            latestNews=it.getString(NEWS_ID)
        }

    }
    var contentLoadMore = true
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
//        initiateRequest()
        rvSetup()
        binding.imgClose.setOnClickListener {
            back()
        }
    }


    private fun subscribeToObservables() {
        newsViewModel.newsPagination.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                newsListAdapter.submitData(it)
            }

        }
        newsViewModel.newsLatest.observe(viewLifecycleOwner){
            latestNewsListAdapter.submitList(it)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        }
    }


    private fun rvSetup() {
        binding.rvHorizontalNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
           latestNewsListAdapter  = LatestNewsListAdapter(
                object :NewsClickListener{
                    override fun rowClickListener(news: LatestNews) {
                        val bundle = bundleOf(NEWS_ID to news.id)
                        navigate(R.id.action_latestNewsFragment_to_newsDetailFragment, bundle)
                    }

                    override fun rowClickListener(news: LatestNews, position: Int) {

                    }

                }
            )
            adapter=latestNewsListAdapter
            val pagerSnapHelper = PagerSnapHelper()
            this.onFlingListener = null
            pagerSnapHelper.attachToRecyclerView(this)
        }

        binding.rvVerticalNews.apply {

            this.isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            newsListAdapter= NewsPagingAdapter(
               rowClickListener =  object :NewsClickListener{
                   override fun rowClickListener(news: LatestNews) {
                       val bundle = bundleOf(NEWS_ID to news.id)
                       navigate(R.id.action_latestNewsFragment_to_newsDetailFragment, bundle)
                   }

                   override fun rowClickListener(news: LatestNews, position: Int) {
                   }
               }
            )

            adapter = newsListAdapter

        }
    }
}
