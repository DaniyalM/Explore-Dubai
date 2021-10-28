package com.app.dubaiculture.ui.postLogin.more.faqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.databinding.FragmentFAQsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.faqs.adapters.FaqsListAdapter
import com.app.dubaiculture.ui.postLogin.more.faqs.adapters.clicklisteners.FaqsItemClickListner
import com.app.dubaiculture.ui.postLogin.more.faqs.viewmodel.FAQsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FAQsFragment : BaseFragment<FragmentFAQsBinding>(), View.OnClickListener {
    private val faqsViewModel: FAQsViewModel by viewModels()
//    var faqsListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    private lateinit var faqsListAdapter: FaqsListAdapter
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFAQsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(faqsViewModel)
        binding.viewModel = faqsViewModel

        backArrowRTL(binding.imgClose)
        binding.imgCancel.setOnClickListener(this)
        binding.imgClose.setOnClickListener(this)
        rvInit()
        subscribeToObservable()
//        rvSetup()
//        search()
    }

    private fun rvInit() {
        binding.rvFaqs.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            faqsListAdapter = FaqsListAdapter(object : FaqsItemClickListner {
                override fun onClickFaqItem(faqItem: FaqItem) {
                    faqsViewModel.updateFaq(faqItem.copy(is_expanded = !faqItem.is_expanded))

                }
            })
            adapter = faqsListAdapter
        }
    }


    private fun subscribeToObservable() {
        faqsViewModel.faqsTitle.observe(viewLifecycleOwner) {
            binding.faqsTitle.text = it
        }

        faqsViewModel.faqs.observe(viewLifecycleOwner) {
            it.let {
                faqsListAdapter.submitList(it)
            }
        }
        faqsViewModel.faq.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                faqsViewModel.updateFaqInList(it)
            }
        }
    }

//    private fun rvSetup() {
//        faqsViewModel.faqs.observe(viewLifecycleOwner) {
//            it?.let {
//                binding.faqsTitle.text = it.faqTitle
//                it.faqItems.map {
//                    tempArrayList.add(it)
//
//                    faqsListAdapter.add(
//                        FAQsItems<ItemFaqsLayoutBinding>(
//                            faqs = it,
//                            resLayout = R.layout.item_faqs_layout,
//                            context = requireContext(),
//                            isArabic = isArabic()
//                        )
//                    )
//                }
//            }
//
//            binding.rvFaqs.apply {
//                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                adapter = faqsListAdapter
//            }
//        }
//    }
//
//    private fun search() {
//        binding.editSearchFaqs.addTextChangedListener(object : MyCustomTextWatcher() {
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val myKey = binding.editSearchFaqs.text.toString().trim()
//                faqsListAdapter.clear()
//                val list = tempArrayList.filter {
//                    it.question.contains(myKey, ignoreCase = true)
//                }
//
//                if (myKey.trim().isNullOrEmpty()) {
//                    binding.imgCancel.visibility = View.GONE
//                    tempArrayList.map {
//                        faqsListAdapter.add(
//                            FAQsItems<ItemFaqsLayoutBinding>(
//                                faqs = it,
//                                resLayout = R.layout.item_faqs_layout,
//                                context = requireContext(),
//                                isArabic = isArabic()
//                            )
//                        )
//                    }
//                } else {
//                    binding.imgCancel.visibility = View.VISIBLE
//                    list.map {
//                        faqsListAdapter.add(
//                            FAQsItems<ItemFaqsLayoutBinding>(
//                                faqs = it,
//                                resLayout = R.layout.item_faqs_layout,
//                                context = requireContext(),
//                                isArabic = isArabic()
//                            )
//                        )
//                    }
//                }
//                faqsListAdapter.notifyDataSetChanged()
//            }
//        })
//    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_close -> {
                back()
            }
            R.id.img_cancel -> {
                hideKeyboard(activity)
                binding.editSearchFaqs.text?.clear()
            }
        }
    }

    // get text
    // clear adapter
    // arraylist get
    // contain
    //filter out setthe list into adapter
    // if remove get old arraylist ot the set adapter
}