package com.app.dubaiculture.ui.postLogin.attractions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.FragmentAttractionListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.components.recylerview.clicklisteners.RecyclerItemClickListener
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListItem
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_attraction_listing.*


@AndroidEntryPoint
class AttractionListingFragment : BaseFragment<FragmentAttractionListingBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()

    //    private var attractionListScreenAdapter: AttractionListScreenAdapter? = null
    private lateinit var attractionCatId: String
    private var searchQuery: String = ""
    private var pageNumber: Int = 0
    private var pageSize: Int = 3
    private lateinit var attractions: ArrayList<Attractions>
    var contentLoaded = false
    var contentLoadMore = true


    companion object {

        var ATTRACTION_CATEG0RY_ID: String = "AttractionCatId"
        var ATTRACTION_DETAIL_ID: String = "Attraction_ID"
        var ATTRACTION_DETAIL_IMAGE: String = "Attraction_IMAGE"
        var ATTRACTION_DETAIL_TITLE: String = "Attraction_TITLE"
        var ATTRACTION_DETAIL_CATEGORY: String = "Attraction_CATEGORY"

        @JvmStatic
        fun newInstance(attractionCatId: String = "") = AttractionListingFragment().apply {
            arguments = Bundle().apply {
                putString(ATTRACTION_CATEG0RY_ID, attractionCatId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isPagerFragment = true
//        contentLoaded = true
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionListingBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(attractionViewModel)
        initRecyclerView()
        callingObservables()
        subscribeToObservables()
    }

    private fun callingObservables() {
        if (!contentLoaded) {
            progressBar.visibility = View.VISIBLE
            attractionViewModel.getAttractionThroughCategory(attractionCatId,
                pageNumber,
                pageSize,
                getCurrentLanguage().language)
            contentLoaded = true
        }

    }

    private fun subscribeToObservables() {
        attractionViewModel.attractionList.observe(viewLifecycleOwner) {

            when (it) {

                is Result.Success -> {
                    progressBar.visibility = View.GONE
                    contentLoadMore = true

                    if (pageNumber < 1) {
                        attractions = it.value as ArrayList<Attractions>
//                        attractionListScreenAdapter?.attractions = attractions
                        groupAdapter.apply {
                            attractions.forEach {
                                add(AttractionListItem<AttractionListItemCellBinding>(attraction = it))
                            }
                        }

                    } else {
                        if (it.value.isEmpty()) {
                            pageNumber -= 1
                        } else {
                            groupAdapter.apply {
                                it.value.forEach {
                                    add(AttractionListItem<AttractionListItemCellBinding>(attraction = it))
                                }
                            }
//                            attractionListScreenAdapter?.attractions = attractions
                        }
                    }
                }
                is Result.Failure -> {
                    progressBar.visibility = View.GONE

                    handleApiError(it, attractionViewModel)
                }

            }

        }
    }


    private fun initRecyclerView() {

        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
//        attractionListScreenAdapter = AttractionListScreenAdapter()
        binding.rvAttractionListing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = groupAdapter

            this.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (contentLoaded) {
                        if (dy > 0) { //check for scroll down
                            (layoutManager as LinearLayoutManager).apply {
                                visibleItemCount = this.getChildCount()
                                totalItemCount = this.getItemCount()
                                pastVisiblesItems = this.findFirstVisibleItemPosition()
                            }


                            if (contentLoadMore) {
                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    contentLoadMore = false
                                    contentLoaded = false
                                    pageNumber += 1
                                    callingObservables()
                                    // Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    }
                }
            })

            this.addOnItemTouchListener(RecyclerItemClickListener(
                activity,
                this,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val attraction = attractions.get(position)
                        navigateByAction(R.id.action_attractionsFragment_to_attractionDetailFragment,
                            Bundle().apply {
                                this.putString(ATTRACTION_DETAIL_ID, attraction.id)
                                this.putString(ATTRACTION_DETAIL_IMAGE, attraction.portraitImage)
                                this.putString(ATTRACTION_DETAIL_TITLE, attraction.title)
                                this.putString(ATTRACTION_DETAIL_CATEGORY, attraction.category)
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