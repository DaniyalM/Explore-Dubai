package com.app.dubaiculture.ui.postLogin.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.search.local.SearchResultItem
import com.app.dubaiculture.databinding.FragmentSearchBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.components.loadstateadapter.DefaultLoadStateAdapter
import com.app.dubaiculture.ui.postLogin.search.adapters.SearchHistoryAdapter
import com.app.dubaiculture.ui.postLogin.search.adapters.SearchItemListAdapter
import com.app.dubaiculture.ui.postLogin.search.adapters.UniSelectionAdapter
import com.app.dubaiculture.ui.postLogin.search.viewmodels.SearchViewModel
import com.app.dubaiculture.utils.hide
import com.app.dubaiculture.utils.pluralize
import com.app.dubaiculture.utils.show
import com.app.dubaiculture.utils.withLoadStateAdapters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var searchItemListAdapter: SearchItemListAdapter
    private lateinit var uniSelectorAdapter: UniSelectionAdapter<SearchTab>
    private val searchViewModel: SearchViewModel by viewModels()

    companion object {
        var selectedPosition: Int = 0
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)


    private fun subscribeToObservable() {

        searchViewModel.tab.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                searchViewModel.updateList(it)
            }
        }
        searchViewModel.tabs.observe(viewLifecycleOwner) {
            uniSelectorAdapter.submitList(it)

        }
        searchViewModel.count.observe(viewLifecycleOwner) {
            val label = activity.resources.getString(R.string.result).pluralize(it)
            binding.count.text = "$it $label found"
        }
        searchViewModel.searchFilter.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                searchViewModel.search(it)
            }
        }
        searchViewModel.searchPaginationItem.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                searchItemListAdapter.submitData(it)
            }
        }

        searchViewModel.stringList.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                binding.clearPop.hide()
            else
                binding.clearPop.show()

            searchHistoryAdapter.submitList(it)
        }
        searchViewModel.viewFlag.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it) {
                    binding.searchHistory.hide()
                    binding.resultView.show()
                } else {
                    binding.resultView.hide()
                    binding.searchHistory.show()
                }
            }
        }
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(searchViewModel)
        binding.searchVm = searchViewModel
        binding.searchToolbar.clear.setOnClickListener {
            binding.searchToolbar.editSearch.setText("")
        }
        subscribeToObservable()
        rvSetup()


    }

    private fun rvSetup() {
        binding.headersRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            uniSelectorAdapter = UniSelectionAdapter(object : UniSelectionAdapter.HeaderSelector {
                override fun onHeaderSelection(tab: SearchTab) {
                    if (selectedPosition != tab.id) {
                        searchViewModel.updateTab(tab.copy(
                            id = tab.id,
                            title = tab.title,
                            isSelected = !tab.isSelected
                        ))
                    }
                }
            })
            adapter = uniSelectorAdapter
        }
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

        binding.ItemRv.apply {
            layoutManager = LinearLayoutManager(context)
            searchItemListAdapter =
                SearchItemListAdapter(object : SearchItemListAdapter.SearchItemClickListner {
                    override fun onSearchItemClick(searchResultItem: SearchResultItem) {

                    }
                })
            adapter = searchItemListAdapter.withLoadStateAdapters(
                DefaultLoadStateAdapter(),
                DefaultLoadStateAdapter(), callback = {
                    binding.noDataPlaceHolder.show(it)
                }
            )
        }
    }
}