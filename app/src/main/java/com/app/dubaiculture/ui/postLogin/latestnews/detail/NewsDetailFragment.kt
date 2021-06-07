package com.app.dubaiculture.ui.postLogin.latestnews.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentNewsDetailBinding
import com.app.dubaiculture.databinding.ItemMoreNewsBinding
import com.app.dubaiculture.databinding.ItemNewsArticleBinding
import com.app.dubaiculture.databinding.ItemSliderBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
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
        subscribeUiEvents(newsDetailViewModel)
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

        newsDetailViewModel.newsDetail.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                binding.tvTitle.text = it.title
                binding.tvDate.text = it.postedDate
                binding.tvDesc.text = it.description
                binding.tvDescBg.text = it.blockQuote.get(0).summary
                binding.tvTitleBg.text = it.blockQuote.get(0).title
                binding.tvMoreDetail.text = it.moreDetail.get(0).summary
                binding.tvMoreTitleDetail.text = it.moreDetail.get(0).title

                groupAdapter.add(
                    NewsSliderItems<ItemSliderBinding>(
                        newsDetail = it,
                        resLayout = R.layout.item_slider,
                        context = requireContext()
                    )
                )
                newsArticleAdapter.add(
                    NewsSliderItems<ItemNewsArticleBinding>(
                        newsDetail = it,
                        resLayout = R.layout.item_news_article,
                        context = requireContext()
                    )
                )

                moreNewsAdapter.add(
                    NewsSliderItems<ItemMoreNewsBinding>(
                        object : RowClickListener {
                            override fun rowClickListener(position: Int) {


                            }

                        }, newsDetail = it, R.layout.item_more_news, requireContext()
                    )
                )

            }


        }



        binding.rvSlider.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)
            binding.indicator.attachToRecyclerView(this, pagerSnapHelper)
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


        binding.rvMoreNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moreNewsAdapter
        }

    }
}