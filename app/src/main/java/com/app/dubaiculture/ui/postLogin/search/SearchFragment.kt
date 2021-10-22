package com.app.dubaiculture.ui.postLogin.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.databinding.FragmentSearchBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.search.adapters.SearchHistoryAdapter
import com.app.dubaiculture.ui.postLogin.search.viewmodels.SearchViewModel
import com.app.dubaiculture.utils.hide
import com.app.dubaiculture.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    private val searchViewModel: SearchViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    private fun subscribeToObservable() {
        searchViewModel.stringList.observe(viewLifecycleOwner) {}
        searchViewModel.viewFlag.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it) {
                    binding.searchHistory.hide()
                } else {
                    binding.searchHistory.show()
                }
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(searchViewModel)
        binding.searchToolbar.viewmodel = searchViewModel
        subscribeToObservable()
        rvSetup()


        val stringList: MutableList<String> = mutableListOf(
            "asdasd1",
            "zxczxc2",
            "wqe3",
            "zxczxc",
            "123",
            "czxcq",
            "zxcqwed",
            "asd12e",
            "czxc",
            "asd1e"
        )
        searchHistoryAdapter.submitList(
            stringList
        )
    }

    private fun rvSetup() {
        binding.popularRv.apply {
            layoutManager = LinearLayoutManager(context)
            searchHistoryAdapter =
                SearchHistoryAdapter(object : SearchHistoryAdapter.SearchHistoryClick {
                    override fun onClick(query: String) {
                        binding.searchToolbar.editSearch.setText(query)
                    }
                })

            adapter = searchHistoryAdapter
        }
    }
}