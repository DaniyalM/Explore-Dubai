package com.app.dubaiculture.ui.postLogin.latestnews.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.*
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsItems
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsVerticalItems
import com.app.dubaiculture.ui.postLogin.latestnews.detail.adapter.NewsSliderItems
import com.app.dubaiculture.ui.postLogin.latestnews.detail.viewmodel.NewsDetailViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {

    private val newsDetailViewModel: NewsDetailViewModel by viewModels()
    private lateinit var newsArticleAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var moreNewsAdapter: GroupAdapter<GroupieViewHolder>


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentNewsDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSetUp()
    }

    private fun rvSetUp() {

        newsArticleAdapter = GroupAdapter()
        moreNewsAdapter = GroupAdapter()
        if (groupAdapter.itemCount > 0) {
            groupAdapter.clear()
        }
        if (newsArticleAdapter.itemCount > 0) {
            newsArticleAdapter.clear()
        }
        if (moreNewsAdapter.itemCount > 0) {
            moreNewsAdapter.clear()
        }
        newsDetailViewModel.newsList().map {
            groupAdapter.add(
                NewsSliderItems<ItemSliderBinding>(
                    latestNews = it,
                    resLayout = R.layout.item_slider,
                    context = requireContext()
                )
            )

        }
        binding.rvSlider.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)
            binding.indicator.attachToRecyclerView(this, pagerSnapHelper)
        }


        newsDetailViewModel.articleList().map {
            newsArticleAdapter.add(
                NewsItems<ItemNewsArticleBinding>(
                    latestNews = it,
                    resLayout = R.layout.item_news_article,
                    context = requireContext()
                )
            )
        }
        binding.rvNewsArticle.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newsArticleAdapter
        }
//
//
//        newsDetailViewModel.newsList().map {
//            groupAdapter.add(
//                NewsItems<ItemsLatestNewsBinding>(
//                    object : RowClickListener {
//                        override fun rowClickListener(position: Int) {
//                            navigate(R.id.action_latestNewsFragment_to_newsDetailFragment)
//
//                        }
//
//                    }, latestNews = it, R.layout.items_latest_news, requireContext()
//                )
//            )
//
//        }
        newsDetailViewModel.morenewsList().map {
            moreNewsAdapter.add(
                NewsVerticalItems<ItemMoreNewsBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {

                        }

                    }, latestNews = it, R.layout.item_more_news, requireContext()
                )
            )
        }


        binding.rvMoreNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moreNewsAdapter
        }

    }
}