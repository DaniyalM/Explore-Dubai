package com.app.dubaiculture.ui.postLogin.more.profile.placesvisited

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
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.FragmentPlacesVisitedBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListItem
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlacesVisitedFragment : BaseFragment<FragmentPlacesVisitedBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentPlacesVisitedBinding.inflate(inflater, container, false)

    private fun subscribeToObservables(){
        attractionViewModel.isFavourite.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    if (TextUtils.equals(it.value.Result.message, "Added")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_fav)
                    }
                    if (TextUtils.equals(it.value.Result.message, "Deleted")) {
                        checkBox.background = getDrawableFromId(R.drawable.heart_icon_home)
                    }
                }
                is Result.Failure -> handleApiError(it, attractionViewModel)
            }
        }
        attractionViewModel.visitedAttractionList.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {
                it.forEach {
                    if (groupAdapter.itemCount>0){
                        groupAdapter.clear()
                    }
                    groupAdapter.add(
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
                                        putParcelable(Constants.NavBundles.ATTRACTION_OBJECT,
                                                it)
                                    })
                                }

                                override fun rowClickListener(position: Int, imageView: ImageView) {

                                }
                            },
                            attraction = it,
                            context = activity,
                            isVisited = true

                    ))
                }
            }
        }
    }

    private fun initiateRequest() {
        binding.swipeRefresh.apply {
            setColorSchemeResources(R.color.colorPrimary,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_blue_dark)
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
                adapter = groupAdapter
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



