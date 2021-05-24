package com.app.dubaiculture.ui.postLogin.more.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentLatestNewsBinding
import com.app.dubaiculture.databinding.ItemsLatestNewsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.more.news.adapter.NewsItems
import com.app.dubaiculture.ui.postLogin.more.news.viewmodel.NewsViewModel
import me.relex.circleindicator.CircleIndicator2


class LatestNewsFragment : BaseFragment<FragmentLatestNewsBinding>(), View.OnClickListener {
    private val newsViewModel : NewsViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLatestNewsBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(newsViewModel)
        rvSetup()
    }
    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    private fun rvSetup(){
        newsViewModel.newsList().map {
            groupAdapter.add(
                NewsItems<ItemsLatestNewsBinding>(
                    object : RowClickListener {
                        override fun rowClickListener(position: Int) {

                        }

                    }, latestNews = it, R.layout.items_latest_news, requireContext()
                )
            )
        }
        binding.rvHorizontalNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = groupAdapter
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)
            binding.indicator.attachToRecyclerView(this,pagerSnapHelper)

        }
    }
}