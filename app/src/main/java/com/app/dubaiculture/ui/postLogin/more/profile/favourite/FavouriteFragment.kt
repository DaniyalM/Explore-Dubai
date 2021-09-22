package com.app.dubaiculture.ui.postLogin.more.profile.favourite

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.profile.local.Favourite
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.FragmentFavouriteBinding
import com.app.dubaiculture.databinding.ItemEventListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListItem
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.events.adapters.EventListItem
import com.app.dubaiculture.ui.postLogin.more.profile.favourite.models.FavouriteHeader
import com.app.dubaiculture.ui.postLogin.more.profile.favourite.services.FavouriteServices
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.NavBundles.FAVOURITE_BUNDLE
import com.app.dubaiculture.utils.handleApiError
import com.squareup.otto.Subscribe
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {
    private lateinit var linearLayoutManger: LinearLayoutManager
    private val profileViewModel: ProfileViewModel by viewModels()
    private  var events: List<Events> =  emptyList()
    private  var attractions: List<Attractions> = emptyList()
    var eventsAdapter: GroupAdapter<GroupieViewHolder>? = GroupAdapter()
    var attractionsAdapter: GroupAdapter<GroupieViewHolder>? = GroupAdapter()

    override fun onDestroy() {
        super.onDestroy()
        eventsAdapter = null
        attractionsAdapter = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            getParcelable<Favourite>(FAVOURITE_BUNDLE)?.let {
                events = it.events
                attractions = it.attractions
            }
        }
    }

    private fun initiateRequest() {

           binding.swipeRefresh.post {
                binding.swipeRefresh.isRefreshing = true
                profileViewModel.getFavourites()
            }



        binding.swipeRefresh.apply {
            setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
            )
            setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                profileViewModel.getFavourites()
            }
        }
    }


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentFavouriteBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(profileViewModel)
        backArrowRTL(binding.headerVisited.back)
        binding.customTextView3.text = activity.resources.getString(R.string.favourites)

        binding.headerVisited.back.setOnClickListener {
            back()
        }

//        setupToolbarWithSearchItems()
        initiateRequest()
        subscribeToObservables()
        initEventRvListing()
        initAttractionRvlisting()
    }

    private fun subscribeToObservables() {
        profileViewModel.favourite.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = false
            when (it) {
                is Result.Success -> {
                    it.value.getContentIfNotHandled()?.let {
                        events = it.events
                        attractions = it.attractions
                        binding.horizontalSelector.initialize(initializeHeaders(), bus)
                    }
                }
                is Result.Failure -> handleApiError(it, profileViewModel)
            }
        }

        profileViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    initiateRequest()
                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_home)
                        profileViewModel.getFavourites()
                    }
                }
                is Result.Failure -> handleApiError(it, profileViewModel)
            }
        }
    }


    private fun initEventRvListing() {
        linearLayoutManger = LinearLayoutManager(activity)
        binding.rveventListing.apply {
            layoutManager = linearLayoutManger
            adapter = eventsAdapter
        }
    }

    private fun initAttractionRvlisting() {
        linearLayoutManger = LinearLayoutManager(activity)
        binding.rvAttractionListing.apply {
            layoutManager = linearLayoutManger
            adapter = attractionsAdapter
        }
    }

//    private fun initRvListing() {
//        linearLayoutManger = LinearLayoutManager(activity)
//        binding.rvListing.apply {
//            layoutManager = linearLayoutManger
//            adapter = groupAdapter
//        }
//
//    }


    private fun initializeHeaders(): MutableList<FavouriteHeader> {
        val placesVisited = ArrayList<FavouriteHeader>()
        repeat(3) {
            val favouriteHeader = FavouriteHeader(selectedIcon = null, unselectedIcon = null)

            when (it) {
                0 -> {
                    favouriteHeader.apply {
                        id = it
                        title = "Events"
                        selectedColor = R.color.purple_900
                        unSelectedColor = R.color.white_900
                        selectedIcon = R.drawable.events
                        unselectedIcon = R.drawable.calender
                    }
                }
                1 -> {
                    favouriteHeader.apply {
                        id = it
                        title = "Attractions"
                        selectedColor = R.color.purple_900
                        unSelectedColor = R.color.white_900
                        selectedIcon = R.drawable.attraction
                        unselectedIcon = R.drawable.icon_visited
                    }
                }
                else -> {
                    favouriteHeader.apply {
                        id = it
                        title = "Services"
                        selectedColor = R.color.purple_900
                        unSelectedColor = R.color.white_900
                        selectedIcon = R.drawable.servicesiconwhite
                        unselectedIcon = R.drawable.servicesicon
                    }
                }
            }
            placesVisited.add(favouriteHeader)

        }


        return placesVisited
    }


    @Subscribe
    fun handleHeaderClick(favouriteServices: FavouriteServices) {
        when (favouriteServices) {
            is FavouriteServices.HeaderItemClick -> {
                when (favouriteServices.position) {
                    0 -> {
                        addEvents()
                    }
                    1 -> {
                        addAttractions()
                    }
                    2 -> {
                        addServices()
                        //Services Will come here
                    }
                }
            }
        }
    }

    private fun setupToolbarWithSearchItems() {
//        binding.headerVisited.apply {
////            profilePic.visibility = View.GONE
////            img_drawer.visibility = View.GONE
//            toolbar_title.apply {
//                visibility = View.VISIBLE
//                text = activity.getString(R.string.favourites)
//            }
//        }
    }

    private fun addServices() {
        binding.rveventListing.visibility = View.GONE
        binding.rvAttractionListing.visibility = View.GONE
        binding.rvServicesListing.visibility = View.VISIBLE
    }

    private fun addEvents() {
        binding.rveventListing.visibility = View.VISIBLE
        binding.rvAttractionListing.visibility = View.GONE
        binding.rvServicesListing.visibility = View.GONE

        eventsAdapter?.apply {
            if (this.itemCount > 0) {
                this.clear()
            }

            events.forEach {
                add(
                    EventListItem<ItemEventListingBinding>(
                        object :
                            FavouriteChecker {
                            override fun checkFavListener(
                                checkbox: CheckBox,
                                pos: Int,
                                isFav: Boolean,
                                itemId: String,
                            ) {
                                favouriteClick(
                                    checkbox,
                                    isFav,
                                    type = 2,
                                    itemId = itemId,
                                    baseViewModel = profileViewModel,
                                    nav = R.id.action_favouriteFragment_to_post_login_bottom_navigation
                                )
                            }

                        },
                        object : RowClickListener {
                            override fun rowClickListener(position: Int) {
                                val eventObj = events[position]
                                val bundle = Bundle()
                                bundle.putParcelable(Constants.NavBundles.EVENT_OBJECT, eventObj)
                                navigate(
                                    R.id.action_favouriteFragment_to_event_detail_navigation,
                                    bundle
                                )
                            }

                            override fun rowClickListener(position: Int, imageView: ImageView) {

                            }

                        },object : EventListItem.SurveySubmitListener{
                            override fun submitBtnClickListener(position: Int) {
                            }

                        },
                        event = it,
                        resLayout = R.layout.item_event_listing,
                        activity,
                        hasSurvey = false
                    )
                )
            }
        }


    }

    private fun addAttractions() {
        binding.rvAttractionListing.visibility = View.VISIBLE
        binding. rveventListing.visibility = View.GONE
        binding.rvServicesListing.visibility = View.GONE
    
        attractionsAdapter?.apply {
            if (this.itemCount > 0) {
                this.clear()
            }
            attractions.forEach {
                add(
                    AttractionListItem<AttractionListItemCellBinding>(
                        favChecker = object : FavouriteChecker {
                            override fun checkFavListener(
                                checkbox: CheckBox,
                                pos: Int,
                                isFav: Boolean,
                                itemId: String,
                            ) {
                                favouriteClick(
                                    checkbox,
                                    isFav,
                                    R.id.action_favouriteFragment_to_post_login_bottom_navigation,
                                    itemId, profileViewModel,
                                    1
                                )
                            }
                        },
                        rowClickListener = object : RowClickListener {
                            override fun rowClickListener(position: Int) {
                                navigate(R.id.action_favouriteFragment_to_attraction_detail_navigation,
                                    Bundle().apply {
                                        putParcelable(
                                            Constants.NavBundles.ATTRACTION_OBJECT,
                                            it
                                        )
                                    })
                            }

                            override fun rowClickListener(position: Int, imageView: ImageView) {

                            }
                        },
                        attraction = it,
                        context = activity,
                        isVisited = false
                    )
                )
            }
        }


    }

}