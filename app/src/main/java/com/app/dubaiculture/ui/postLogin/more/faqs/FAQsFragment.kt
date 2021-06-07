package com.app.dubaiculture.ui.postLogin.more.faqs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.databinding.FragmentFAQsBinding
import com.app.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.app.dubaiculture.databinding.ItemNewsArticleBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.latestnews.adapter.NewsItems
import com.app.dubaiculture.ui.postLogin.latestnews.viewmodel.NewsViewModel
import com.app.dubaiculture.ui.postLogin.more.faqs.viewmodel.FAQsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_back.view.*

@AndroidEntryPoint
class FAQsFragment : BaseFragment<FragmentFAQsBinding>() {
    private val faqsViewModel: FAQsViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFAQsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(faqsViewModel)
        rvSetup()
      binding.imgClose.setOnClickListener {
          back()
      }
    }

    private fun rvSetup() {
        faqsViewModel.faqs(getCurrentLanguage().language)
        faqsViewModel.faqs.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                binding.faqsTitle.text = it.faqTitle
                it.faqItems.map {

                groupAdapter.add(
                    FAQsItems<ItemFaqsLayoutBinding>(
                        faqs = it,
                        resLayout = R.layout.item_faqs_layout,
                        context = requireContext(),
                        isArabic = isArabic()
                    )
                )
            }
            }

            binding.rvFaqs.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = groupAdapter
            }
        }
    }
}