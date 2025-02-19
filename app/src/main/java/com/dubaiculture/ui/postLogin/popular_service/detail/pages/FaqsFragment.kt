package com.dubaiculture.ui.postLogin.popular_service.detail.pages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.dubaiculture.R
import com.dubaiculture.data.repository.more.local.FaqItem
import com.dubaiculture.data.repository.popular_service.local.models.Description
import com.dubaiculture.data.repository.popular_service.local.models.FAQ
import com.dubaiculture.databinding.ItemsServiceDetailInnerFaqsListingLayoutBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.more.faqs.adapters.FaqsListAdapter
import com.dubaiculture.ui.postLogin.more.faqs.adapters.clicklisteners.FaqsItemClickListner
import com.dubaiculture.ui.postLogin.popular_service.detail.pages.viewmodels.FaqsViewModel
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.hide
import com.dubaiculture.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaqsFragment :
    BaseFragment<ItemsServiceDetailInnerFaqsListingLayoutBinding>() {
    private lateinit var fAQs: List<FAQ>
    private lateinit var faqsListAdapter: FaqsListAdapter
    private val faqsViewModel: FaqsViewModel by viewModels()

    companion object {
        fun newInstance(fAQs: List<FAQ>): FaqsFragment {
            val args = Bundle()
            args.putParcelableArrayList(Constants.NavBundles.FAQS_LIST, ArrayList(fAQs))
            val fragment = FaqsFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            fAQs= it.getParcelableArrayList(Constants.NavBundles.FAQS_LIST)!!
        }
    }

    private fun rvInit() {
        binding.innerRecyclerView.apply {
            if (fAQs.get(0).fAQs.isEmpty()){
                hide()
                binding.detailListingHeader.hide()
                binding.tvPlaceHolder.show()
            }else{
                binding.tvPlaceHolder.hide()
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                faqsListAdapter = FaqsListAdapter(object : FaqsItemClickListner {
                    override fun onClickFaqItem(faqItem: FaqItem) {
                        faqsViewModel.updateFaq(faqItem.copy(is_expanded = !faqItem.is_expanded))

                    }
                })
                adapter = faqsListAdapter
                faqsViewModel.setFaqs(fAQs.get(0).fAQs)
            }

        }

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

    }


}