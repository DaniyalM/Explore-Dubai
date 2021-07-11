package com.app.dubaiculture.ui.postLogin.latestnews.detail

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.news.local.LatestNews
import com.app.dubaiculture.data.repository.news.local.NewsDetail
import com.app.dubaiculture.databinding.FragmentNewsDetailBinding
import com.app.dubaiculture.databinding.ItemMoreNewsBinding
import com.app.dubaiculture.databinding.ItemSliderBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsItems
import com.app.dubaiculture.ui.postLogin.latestnews.detail.adapter.NewsArticleAdapter
import com.app.dubaiculture.ui.postLogin.latestnews.detail.adapter.NewsSliderItems
import com.app.dubaiculture.ui.postLogin.latestnews.detail.viewmodel.NewsDetailViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.NEWS_ID
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {
    var newsListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    var latestNews: LatestNews? = null
    private val newsDetailViewModel: NewsDetailViewModel by viewModels()
    private lateinit var newsArticleAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var moreNewsAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var articleAdapter: RecyclerView.Adapter<*>
    private lateinit var newsDetails : NewsDetail
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                TextToSpeech(requireContext()) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        textToSpeechEngine.language = Locale(getCurrentLanguage().language)
                    }
                }
            }
        }
    }
    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentNewsDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(newsDetailViewModel)
        backArrowRTL(binding.imgClose)
        arguments?.let {
            newsDetailViewModel.newsDetail(
                id = it.getString(NEWS_ID).toString(),
                locale = getCurrentLanguage().language
            )

        }
        rvSetUp()
        binding.imgClose.setOnClickListener {
            back()
        }
        binding.imgSpeaker.setOnClickListener {
            if(!newsDetails.title.isNullOrEmpty())
            textToSpeechEngine.speak(
                newsDetails.title,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "tts1"
            )
        }
    }

    private fun rvSetUp() {

        newsArticleAdapter = GroupAdapter()
        moreNewsAdapter = GroupAdapter()
        if (newsListAdapter.itemCount > 0) {
            newsListAdapter.clear()
        }
        if (newsArticleAdapter.itemCount > 0) {
            newsArticleAdapter.clear()
        }
        if (moreNewsAdapter.itemCount > 0) {
            moreNewsAdapter.clear()
        }

        newsDetailViewModel.newsDetail.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                newsDetails =it
                binding.tvTitle.text = it.title
                binding.tvDate.text = it.postedDate
                binding.tvDesc.text = it.description
                if (!it.blockQuote.isNullOrEmpty()) {
                    binding.tvDescBg.text = it.blockQuote[0].summary
                    binding.tvTitleBg.text = it.blockQuote[0].title
                } else {
                    binding.llDescBg.visibility = View.GONE
                }
                if (!it.moreDetail.isNullOrEmpty()) {
                    binding.tvMoreDetail.text = it.moreDetail[0].summary
                    binding.tvMoreTitleDetail.text = it.moreDetail[0].title
                } else {
                    binding.tvMoreDetail.visibility = View.GONE
                    binding.tvMoreTitleDetail.visibility = View.GONE
                }

                articleAdapter = NewsArticleAdapter(it.tags!!)
                binding.rvNewsArticle.apply {
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    adapter = articleAdapter
                }

                newsListAdapter.add(
                    NewsSliderItems<ItemSliderBinding>(
                        newsDetail = it,
                        resLayout = R.layout.item_slider,
                        context = requireContext()
                    )
                )


                if (!it.relatedData.isNullOrEmpty()) {
                    it.relatedData.map {
                        moreNewsAdapter.add(
                            NewsItems<ItemMoreNewsBinding>(
                                object : RowClickListener {
                                    override fun rowClickListener(position: Int) {


                                    }

                                    override fun rowClickListener(position: Int, imageView: ImageView) {

                                    }

                                }, latestNews = it, R.layout.item_more_news, requireContext()
                            )
                        )

                    }

                } else {
                    binding.llMoreNews.visibility = View.GONE
                }
            }


        }



        binding.rvSlider.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = newsListAdapter
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(this)
            binding.indicator.attachToRecyclerView(this, pagerSnapHelper)
        }





        binding.rvMoreNews.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = moreNewsAdapter
        }

    }
    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }
    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }
}