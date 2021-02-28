package com.app.dubaiculture.ui.postLogin.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.components.AttractionHeaderItemSelector.Companion.clickCheckerFlag
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout.view.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAttractionsBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        setupToolbarWithSearchItems()
        subscribeUiEvents(attractionViewModel)
        callingObservables()
        subscribeToObservables()
        initiatePager()


    }

    private fun initiatePager() {
        binding.pager.isUserInputEnabled = false
    }


    private fun callingObservables() {
        lifecycleScope.launch {
            attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
        }
    }

    private fun subscribeToObservables() {
        attractionViewModel.attractionCategoryList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let {
                        binding.horizontalSelector.initialize(it.value, binding.pager)
                        binding.pager.adapter = AttractionPagerAdaper(this,
                            it.value as ArrayList<AttractionCategory>)

                    }
                }
                is Result.Failure -> {
                    handleApiError(it, attractionViewModel)
                }
            }
        }
    }


    private fun setupToolbarWithSearchItems() {
//        var searchViewVisibility = false
        binding.root.apply {
            profilePic.visibility = View.GONE
            img_drawer.visibility = View.GONE
            toolbar_title.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.attractions)
            }
//            search.setOnClickListener {
//                searchViewVisibility = !searchViewVisibility
//                if (searchViewVisibility) {
//                    binding.root.searchView.visibility = View.VISIBLE
//                    toolbar_title.visibility = View.GONE
//                } else {
//                    binding.root.searchView.visibility = View.GONE
//                    toolbar_title.visibility = View.VISIBLE
//                }
//            }
//
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(p0: String?) = true
//                override fun onQueryTextChange(text: String?): Boolean {
//                    text?.let { bus.post(AttractionBusService().SearchTextQuery(it)) }
//                    return true
//                }
//            })

        }
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalSelector.positionUpdate(clickCheckerFlag)
    }

}

