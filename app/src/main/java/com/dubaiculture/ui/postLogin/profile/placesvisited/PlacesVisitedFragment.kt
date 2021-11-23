package com.dubaiculture.ui.postLogin.profile.placesvisited

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.databinding.AttractionListItemCellBinding
import com.dubaiculture.databinding.FragmentPlacesVisitedBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.attractions.adapters.AttractionListItem
import com.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.dubaiculture.utils.Constants
import com.dubaiculture.utils.handleApiError
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlacesVisitedFragment : BaseFragment<FragmentPlacesVisitedBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()
    var placesVisitedListAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPlacesVisitedBinding.inflate(inflater, container, false)

    private fun subscribeToObservables() {
        attractionViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox?.background = getDrawableFromId(R.drawable.heart_icon_home)
                    }
                }
                is Result.Failure -> handleApiError(it, attractionViewModel)
            }
        }
        attractionViewModel.visitedAttractionList.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                if (it.isEmpty()) {

                    binding.personalRv.visibility = View.GONE
                    binding.tvPlaceHolder.visibility = View.VISIBLE

                } else {

                    binding.tvPlaceHolder.visibility = View.GONE
                    binding.personalRv.visibility = View.VISIBLE

                    it.forEach {
                        if (placesVisitedListAdapter.itemCount > 0) {
                            placesVisitedListAdapter.clear()
                        }
                        placesVisitedListAdapter.add(
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
                                            R.id.action_placesVisited_to_post_login_bottom_navigation,
                                            itemId, attractionViewModel,
                                            1
                                        )
                                    }
                                },
                                rowClickListener = object : RowClickListener {
                                    override fun rowClickListener(position: Int) {
                                        navigate(R.id.action_placesVisited_to_attraction_detail_navigation,
                                            Bundle().apply {
                                                putParcelable(
                                                    Constants.NavBundles.ATTRACTION_OBJECT,
                                                    it
                                                )
                                            })
                                    }

                                    override fun rowClickListener(
                                        position: Int,
                                        imageView: ImageView
                                    ) {
                                    }
                                },
                                attraction = it,
                                context = activity,
                                isVisited = true
                            )
                        )
                    }
                }
            }
        }
    }

    private fun initiateRequest() {
        binding.swipeRefresh.apply {
            attractionViewModel.getVisitedAttractions(getCurrentLanguage().language)
            setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
            )
            setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                attractionViewModel.getVisitedAttractions(getCurrentLanguage().language)

            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(attractionViewModel)
        initiateRequest()
        binding.headerVisited.apply {
            back.apply {
                backArrowRTL(this)
                setOnClickListener {
                    back()
                }
            }
        }

        attractionViewModel.getVisitedAttractions(getCurrentLanguage().language)
        binding.apply {
            personalRv.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = placesVisitedListAdapter
            }
            subscribeToObservables()
        }
    }
//    private fun testPlaces():MutableList<Attractions>{
//        val placesVisited= ArrayList<Attractions>()
//        repeat(10){
//
//            placesVisited.add(
//                    Attractions(
//                            id = it.toString(),
//                            title = it.toString(),
//                            locationTitle = it.toString(),
//                            category = it.toString(),
//                    )
//            )
//
//        }
//
//
//        return placesVisited
//    }
}



