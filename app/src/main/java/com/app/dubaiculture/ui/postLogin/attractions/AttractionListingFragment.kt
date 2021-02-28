package com.app.dubaiculture.ui.postLogin.attractions

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.explore.local.models.BaseModel
import com.app.dubaiculture.databinding.FragmentAttractionListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListScreenAdapter
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionBusService
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AttractionListingFragment : BaseFragment<FragmentAttractionListingBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()
    private var attractionListScreenAdapter: AttractionListScreenAdapter? = null

    private var attractionCategoryTag: String = ""
    private var searchQuery: String = ""


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionListingBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        binding.fragName.text = attractionCategoryTag
        subscribeUiEvents(attractionViewModel)


        initRecyclerView()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(ATTRACTION_CATEG0RY_TYPE)?.let {
            attractionCategoryTag = it
        }

    }

    @Subscribe
    fun onSearchTextQueryChange(updatedText: AttractionBusService.SearchTextQuery) {
        searchQuery = updatedText.text.trim()
        attractionListScreenAdapter?.search(searchQuery){
            attractionViewModel.showToast("No Results Found")
        }
    }


    companion object {

        var ATTRACTION_CATEG0RY_TYPE: String = "Attractions"

        @JvmStatic
        fun newInstance(attractions: ArrayList<Attractions>) = AttractionListingFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ATTRACTION_CATEG0RY_TYPE,attractions)
            }
        }
    }

    private fun subscribeToObservable() {

    }


    private fun createTestItem(): List<BaseModel> {
        var baseModel:ArrayList<BaseModel> = ArrayList()
        for (i in 0..10){
            baseModel.add(BaseModel().apply {
                this.id = i.toString()
                this.title = "title $i"
                this.category = "category $i"
            })
        }


        return baseModel

    }


    private fun initRecyclerView() {
        attractionListScreenAdapter = AttractionListScreenAdapter()

        binding.rvAttractionListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter=attractionListScreenAdapter
            attractionListScreenAdapter?.attractions=createTestItem()

        }


    }

}