package com.app.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.data.repository.popular_service.local.models.FAQ
import com.app.dubaiculture.databinding.ItemsServiceDetailInnerFaqsListingLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.faqs.adapters.FaqsListAdapter
import com.app.dubaiculture.ui.postLogin.more.faqs.adapters.clicklisteners.FaqsItemClickListner
import com.app.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.FaqsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaqsFragment(val fAQs: List<FAQ>, val forumPager: ViewPager2) :
    BaseFragment<ItemsServiceDetailInnerFaqsListingLayoutBinding>() {
    private lateinit var faqsListAdapter: FaqsListAdapter
    private val faqsViewModel: FaqsViewModel by viewModels()
    private lateinit var observer: RecyclerView.AdapterDataObserver

    private fun rvInit() {
        binding.innerRecyclerView.apply {
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
                    showLoader(faqsListAdapter.itemCount == 0)
                }
            }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            faqsListAdapter = FaqsListAdapter(object : FaqsItemClickListner {
                override fun onClickFaqItem(faqItem: FaqItem) {
                    faqsViewModel.updateFaq(faqItem.copy(is_expanded = !faqItem.is_expanded))

                }
            })
            adapter = faqsListAdapter
            faqsListAdapter.registerAdapterDataObserver(observer)
            faqsViewModel.setFaqs(fAQs.get(0).fAQs)
        }

        binding.innerRecyclerView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (v?.id == forumPager.id) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
                        binding.innerRecyclerView.requestDisallowInterceptTouchEvent(false)
                    } else {
                        binding.innerRecyclerView.requestDisallowInterceptTouchEvent(true)
                    }
                }

                return true

            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        faqsListAdapter.unregisterAdapterDataObserver(observer)
    }

    private fun subscribeToObservable() {
        faqsViewModel.faq.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                faqsViewModel.updateFaqInList(it)
            }
        }
        faqsViewModel.faqs.observe(viewLifecycleOwner) {
            it.let {
                faqsListAdapter.submitList(it)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = ItemsServiceDetailInnerFaqsListingLayoutBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailListingHeader.text = activity.resources.getString(R.string.faqs)
        subscribeToObservable()
        rvInit()

//        initRecycling()
    }

//    fun initRecycling() {
//        binding.innerRecyclerView.apply {
//            val linearLayoutManager = LinearLayoutManager(activity)
//            layoutManager = linearLayoutManager
//            val faqInnerAdapter = GroupAdapter<GroupieViewHolder>()
//            adapter = faqInnerAdapter
//            fAQs.forEach {
//                val faqItem =
//                    ServiceDetailListingItems<ItemsServiceDetailInnerFaqsListingLayoutBinding, FAQ>(
//                        eService = it,
//                        resLayout = R.layout.items_service_detail_inner_faqs_listing_layout
//                    )
//
//                faqInnerAdapter.add(faqItem)
//
//            }
//        }
//
//    }
}