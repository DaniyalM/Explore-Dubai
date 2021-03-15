package com.app.dubaiculture.ui.postLogin.attractions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.FragmentAttractionListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.components.recylerview.clicklisteners.RecyclerItemClickListener
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListScreenAdapter
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AttractionListingFragment : BaseFragment<FragmentAttractionListingBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()
    private var attractionListScreenAdapter: AttractionListScreenAdapter? = null

    //    private lateinit var attractions: ArrayList<Attractions>
    private lateinit var attractionCatId: String
    private var searchQuery: String = ""
    private var pageNumber: Int = 1
    private var pageSize: Int = 10


    companion object {

        var ATTRACTION_CATEG0RY_TYPE: String = "Attractions"
        var ATTRACTION_CATEG0RY_ID: String = "AttractionCatId"
        var ATTRACTION_DETAIL_ID: String = "Attraction_ID"

        @JvmStatic
        fun newInstance(attractionCatId: String = "") = AttractionListingFragment().apply {
            arguments = Bundle().apply {
                putString(ATTRACTION_CATEG0RY_ID, attractionCatId)
            }
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionListingBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(attractionViewModel)
        initRecyclerView()
        Timber.e("AttractionCategoryId : $attractionCatId")
//        callingObservables()
//        binding.swipeRefresh.setOnRefreshListener {
//            binding.swipeRefresh.isRefreshing = false
//            bus.post(AttractionBusService().SwipeToRefresh(true))
//        }
//        attractionViewModel.showToast(attractionId)

    }

    private fun callingObservables() {
        attractionViewModel.getAttractionThroughCategory(attractionCatId, pageNumber, pageSize, getCurrentLanguage().language)

        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        attractionViewModel.attractionList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    attractionListScreenAdapter?.attractions = it.value
                }
                is Result.Failure -> {
                    handleApiError(it, attractionViewModel)
                }

            }

        }
    }


    private fun initRecyclerView() {
        attractionListScreenAdapter = AttractionListScreenAdapter()
        binding.rvAttractionListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = attractionListScreenAdapter
//            val items = createAttractionItems()
//            attractionListScreenAdapter?.attractions = items
            this.addOnItemTouchListener(RecyclerItemClickListener(
                activity,
                this,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
//                        attractionViewModel.showErrorDialog(message = attractions.get(position).title)
                        navigateByAction(R.id.action_attractionsFragment_to_attractionDetailFragment,
                            Bundle().apply {
//                                this.putString(ATTRACTION_DETAIL_ID,
//                                    items.get(position).id)
                            })
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                    }
                }
            ))
        }
    }


    private fun createAttractionItems(): ArrayList<Attractions> =
        mutableListOf<Attractions>().apply {
            repeat((1..4).count()) {
                add(
                    Attractions(
                        id = it.toString(),
                        title = "Museum of the Poet Al Oqaili",
                        category = "BOOKING AVAILABLE",
                        IsFavourite = it % 2 == 0,
                    )
                )
            }
        } as ArrayList<Attractions>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            getString(ATTRACTION_CATEG0RY_ID)?.let {
                attractionCatId = it
            }
        }
    }
}