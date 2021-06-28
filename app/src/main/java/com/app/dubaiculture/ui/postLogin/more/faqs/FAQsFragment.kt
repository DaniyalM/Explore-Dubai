package com.app.dubaiculture.ui.postLogin.more.faqs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.more.local.FaqItem
import com.app.dubaiculture.databinding.FragmentFAQsBinding
import com.app.dubaiculture.databinding.ItemFaqsLayoutBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.more.faqs.viewmodel.FAQsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FAQsFragment : BaseFragment<FragmentFAQsBinding>() ,View.OnClickListener{
    private val faqsViewModel: FAQsViewModel by viewModels()
    private val tempArrayList = ArrayList<FaqItem>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFAQsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(faqsViewModel)
        backArrowRTL(binding.imgClose)
        binding.imgCancel.setOnClickListener(this)
        binding.imgClose.setOnClickListener(this)
        rvSetup()
        search()
    }

    private fun rvSetup() {
        faqsViewModel.faqs(getCurrentLanguage().language)
        faqsViewModel.faqs.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {

                binding.faqsTitle.text = it.faqTitle
                it.faqItems.map {
                    tempArrayList.add(it)

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

    private fun search() {
        binding.editSearchFaqs.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val myKey = binding.editSearchFaqs.text.toString().trim()
                groupAdapter.clear()
                val list = tempArrayList.filter {
                    it.question.contains(myKey, ignoreCase = true)
                }

                if (myKey.trim().isNullOrEmpty()) {
                    binding.imgCancel.visibility= View.GONE
                    tempArrayList.map {
                        groupAdapter.add(
                            FAQsItems<ItemFaqsLayoutBinding>(
                                faqs = it,
                                resLayout = R.layout.item_faqs_layout,
                                context = requireContext(),
                                isArabic = isArabic()
                            )
                        )
                    }
                }else{
                    binding.imgCancel.visibility= View.VISIBLE
                    list.map {
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
                groupAdapter.notifyDataSetChanged()
            }

        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_close ->{
                back()
            }
            R.id.img_cancel ->{
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