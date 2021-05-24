package com.app.dubaiculture.ui.postLogin.more.profile.placesvisited

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.FragmentPlacesVisitedBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListItem
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants

class PlacesVisitedFragment : BaseFragment<FragmentPlacesVisitedBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentPlacesVisitedBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            header.back.setOnClickListener {
                back()
            }
            placesVisitedRv.apply {
                isNestedScrollingEnabled = false
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = groupAdapter
            }

        }

        testPlaces().forEach {
            groupAdapter.add(AttractionListItem<AttractionListItemCellBinding>(
                    favChecker = object : FavouriteChecker {
                        override fun checkFavListener(
                                checkbox: CheckBox,
                                pos: Int,
                                isFav: Boolean,
                                itemId: String,
                        ) {
//                            favouriteClick(
//                                    checkbox,
//                                    isFav,
//                                    R.id.action_attractionsFragment_to_postLoginFragment,
//                                    itemId, attractionViewModel,
//                                    1
//                            )
                        }
                    },
                    rowClickListener = object : RowClickListener {
                        override fun rowClickListener(position: Int) {
//                            navigate(R.id.action_attractionsFragment_to_attractionDetailFragment,
//                                    Bundle().apply {
//                                        putParcelable(Constants.NavBundles.ATTRACTION_OBJECT,
//                                                it)
//                                    })
                        }
                    },
                    attraction = it,
                    context = activity,
                    isVisited = true

            ))
        }

    }





    private fun testPlaces():MutableList<Attractions>{
        val placesVisited= ArrayList<Attractions>()
        repeat(10){

            placesVisited.add(
                    Attractions(
                            id = it.toString(),
                            title = it.toString(),
                            locationTitle = it.toString(),
                            category = it.toString(),
                    )
            )

        }


        return placesVisited
    }
}



