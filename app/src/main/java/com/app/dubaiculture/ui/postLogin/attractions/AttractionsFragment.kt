package com.app.dubaiculture.ui.postLogin.attractions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.utils.AppConfigUtils.clickCheckerFlag
import com.app.dubaiculture.utils.Constants.NavBundles.ATTRACTION_CAT_OBJECT
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout.view.*


@AndroidEntryPoint
class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()
    private lateinit var attractionCategorys: List<AttractionCategory>


//    private var binding: FragmentAttractionsBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            clickCheckerFlag = getInt(ATTRACTION_CAT_OBJECT)
        }
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAttractionsBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbarWithSearchItems()
        subscribeUiEvents(attractionViewModel)
        callingObservables()
        subscribeToObservables()
        initiatePager()
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
        }
    }

    private fun initiatePager() {
        binding.pager.isUserInputEnabled = false
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)
//        binding.swipeRefresh.setOnRefreshListener(null)
//        binding.swipeRefresh.setOnRefreshListener {
//            callingObservables()
//            binding.swipeRefresh.isRefreshing = false
//        }
    }


    private fun callingObservables() {
        if (!this::attractionCategorys.isInitialized) {
            binding.swipeRefresh.post {
                binding.swipeRefresh.isRefreshing = true
                attractionViewModel.getAttractionCategoryToScreen(getCurrentLanguage().language)
            }
        }
    }

    private fun subscribeToObservables() {
        attractionViewModel.attractionCategoryList.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false

            when (it) {
                is Result.Success -> {
                    it.let { result ->
                        attractionCategorys = result.value
                        binding.let {
                            it.pager.isSaveEnabled = false
                            it.horizontalSelector.initialize(result.value, binding.pager, this)

                        }

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
        }
    }

    override fun onResume() {
        super.onResume()
        binding.horizontalSelector.positionUpdate(clickCheckerFlag)
    }


}

