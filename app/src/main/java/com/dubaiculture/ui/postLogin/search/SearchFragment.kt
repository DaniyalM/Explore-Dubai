
package com.dubaiculture.ui.postLogin.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.BuildConfig
import com.dubaiculture.R
import com.dubaiculture.data.repository.search.local.SearchResultItem
import com.dubaiculture.data.repository.search.local.SearchTab
import com.dubaiculture.databinding.FragmentSearchBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.components.loadstateadapter.DefaultLoadStateAdapter
import com.dubaiculture.ui.postLogin.search.adapters.SearchHistoryAdapter
import com.dubaiculture.ui.postLogin.search.adapters.SearchItemListAdapter
import com.dubaiculture.ui.postLogin.search.adapters.UniSelectionAdapter
import com.dubaiculture.ui.postLogin.search.viewmodels.SearchSharedViewModel
import com.dubaiculture.ui.postLogin.search.viewmodels.SearchViewModel
import com.dubaiculture.utils.*
import com.dubaiculture.utils.event.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private lateinit var searchItemListAdapter: SearchItemListAdapter
    private lateinit var uniSelectorAdapter: UniSelectionAdapter<SearchTab>
    private val searchViewModel: SearchViewModel by viewModels()
    private val searchShareViewModel: SearchSharedViewModel by activityViewModels()
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    var tempTab: SearchTab? = null

    private fun registerForActivityResult() {
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    data!!.apply {
                        getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.let {
                            binding.searchToolbar.editSearch.setText(it[0])
                        }

                    }

                } else {
                    searchViewModel.showToast("Task Cancelled")
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerForActivityResult()
    }



    private fun getSpeechInput() {
        val intent = Intent(
            RecognizerIntent
                .ACTION_RECOGNIZE_SPEECH
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE,
            Locale.getDefault()
        )
        binding.searchToolbar.editSearch.setText("")
        startForResult.launch(intent)
    }

    companion object {
        var selectedPosition: Int = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        searchShareViewModel._isAtoZ.value = Event(false)
        searchShareViewModel._isZtoA.value = Event(false)
        searchShareViewModel._isOld.value = Event(false)
        searchShareViewModel._isNew.value = Event(false)
        searchShareViewModel._isAtoZDone.value = Event(false)
        searchShareViewModel._isZtoADone.value = Event(false)
        searchShareViewModel._isOldDone.value = Event(false)
        searchShareViewModel._isNewDone.value = Event(false)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)


    private fun subscribeToObservable() {

        searchShareViewModel.isOldDone.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it)
                    searchViewModel.updateIsOldData(true)
            }
        }
        searchShareViewModel.isNewDone.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it)
                    searchViewModel.updateIsOldData(false)

            }
        }
        searchShareViewModel.isZtoADone.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it)
                    searchViewModel.updateSorting(false)

            }
        }
        searchShareViewModel.isAtoZDone.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {
                if (it)
                    searchViewModel.updateSorting(true)


            }
        }
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
            binding.count.text = "$it $label ${resources.getString(R.string.found)}"
        }
        searchViewModel.searchFilter.observe(viewLifecycleOwner) {
            it?.getContentIfNotHandled()?.let {

                searchViewModel.search(it.copy(culture = getCurrentLanguage().language))
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


                    if (!application.auth.isGuest) {
                        binding.resultView.hide()
                        binding.searchHistory.show()
                    }


                }
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(searchViewModel)


        if (tempTab != null) {
            searchViewModel.updateTab(
                tempTab!!.copy(
                    id = tempTab!!.id,
                    title = tempTab!!.title,
                    isSelected = !tempTab!!.isSelected
                )
            )
            searchViewModel.updateCategoryData(tempTab!!.id.toString())
        }

        binding.searchToolbar.clear.setOnClickListener {
            back()
//            binding.searchToolbar.editSearch.setText("")
//            if (!application.auth.isGuest) {
//                searchViewModel.getSearchHistory()
//            }
        }
        binding.clearPop.setOnClickListener {
            searchViewModel.clearHistory()
        }
//        binding.searchToolbar.editSearch.addTextChangedListener(
//            EndTypingWatcher {
//                val text = binding.searchToolbar.editSearch.text.toString()
//                hideKeyboard(activity)
//                searchViewModel.updateKeyword(text)
//            }
//        )
        binding.searchToolbar.editSearch.addTextChangedListener {
            val text = binding.searchToolbar.editSearch.text.toString()
            binding.searchToolbar.etCross.show(!text.isEmpty())
            binding.searchToolbar.speechSearch.show(text.isEmpty())
//            searchViewModel.updateKeyword(text)
        }
        binding.searchToolbar.editSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchViewModel.updateKeyword(binding.searchToolbar.editSearch.text.toString())
                hideKeyboard(activity)
                return@OnEditorActionListener true
            }
            false
        })
        binding.searchToolbar.imgSearch.setOnClickListener {
            searchViewModel.updateKeyword(binding.searchToolbar.editSearch.text.toString())
        }
        binding.searchToolbar.speechSearch.setOnClickListener {
            getSpeechInput()
        }
        binding.searchToolbar.etCross.setOnClickListener {
            binding.searchToolbar.editSearch.setText("")
            searchViewModel.updateKeyword("")
        }
        binding.ivSort.setOnClickListener {
            navigateByDirections(SearchFragmentDirections.actionSearchFragmentToSortFragment())
        }
        subscribeToObservable()
        if (!this::uniSelectorAdapter.isInitialized) {
            rvSetup()
        }

    }

    private fun rvSetup() {
        binding.headersRv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            uniSelectorAdapter = UniSelectionAdapter(object : UniSelectionAdapter.HeaderSelector {
                override fun onHeaderSelection(tab: SearchTab) {

                    if (selectedPosition != tab.id) {
                        tempTab = tab
                        searchViewModel.updateTab(
                            tab.copy(
                                id = tab.id,
                                title = tab.title,
                                isSelected = !tab.isSelected
                            )
                        )
                        searchViewModel.updateCategoryData(tab.id.toString())
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
                        searchViewModel.updateKeyword(query)
                    }
                })
            adapter = searchHistoryAdapter
        }

        binding.ItemRv.apply {
            layoutManager = LinearLayoutManager(context)
            searchItemListAdapter =
                SearchItemListAdapter(object : SearchItemListAdapter.SearchItemClickListner {
                    override fun onSearchItemClick(searchResultItem: SearchResultItem) {
                        when (searchResultItem.type) {
                            "News" -> {
                                navigateByDirections(
                                    SearchFragmentDirections.actionSearchFragmentToNews(
                                        searchResultItem.id
                                    )
                                )
                            }
                            "Attractions" -> {
                                navigateByDirections(
                                    SearchFragmentDirections.actionSearchFragmentToAttraction(
                                        searchResultItem.id
                                    )
                                )
                            }
                            "Events" -> {
                                navigateByDirections(
                                    SearchFragmentDirections.actionSearchFragmentToEvents(
                                        searchResultItem.id
                                    )
                                )
                            }
                            "EServices" -> {
                                navigateByDirections(
                                    SearchFragmentDirections.actionSearchFragmentToServices(
                                        searchResultItem.id
                                    )
                                )
                            }
                            else -> {

                                navigateByDirections(
                                    SearchFragmentDirections.actionSearchFragmentToWebViewFragment(
                                        BuildConfig.BASE_URL_SHARE + searchResultItem.detailPageUrl,
                                        false
                                    )
                                )
//                                openUrl(
//                                    url = BuildConfig.BASE_URL_SHARE + searchResultItem.detailPageUrl,
//                                    context = activity,
//                                    isWeb = true
//                                )
                            }
                        }

                    }
                })
            adapter = searchItemListAdapter.withLoadStateAdapters(
                DefaultLoadStateAdapter(),
                DefaultLoadStateAdapter(), callback = {
                    if (binding.searchToolbar.editSearch.text.toString().isNotEmpty())
                        binding.noDataPlaceHolder.show(it)
                    else
                        binding.noDataPlaceHolder.hide()

                }
            )
        }
    }
}