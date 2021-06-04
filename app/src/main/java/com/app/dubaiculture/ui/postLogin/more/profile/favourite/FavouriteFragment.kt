package com.app.dubaiculture.ui.postLogin.more.profile.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentFavouriteBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.services.AttractionServices
import com.app.dubaiculture.ui.postLogin.more.profile.favourite.models.FavouriteHeader
import com.app.dubaiculture.ui.postLogin.more.profile.viewmodels.ProfileViewModel
import com.squareup.otto.Subscribe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout.view.*

@AndroidEntryPoint
class FavouriteFragment : BaseFragment<FragmentFavouriteBinding>() {
    //    private lateinit var linearLayoutManger: LinearLayoutManager
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentFavouriteBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(profileViewModel)
        setupToolbarWithSearchItems()
        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        binding.horizontalSelector.initialize(testPlaces(), bus)
    }

//    private fun initRvListing() {
//        linearLayoutManger = LinearLayoutManager(activity)
//        binding.rvListing.apply {
//            layoutManager = linearLayoutManger
//            adapter = groupAdapter
//        }
//
//    }


    private fun testPlaces(): MutableList<FavouriteHeader> {
        val placesVisited = ArrayList<FavouriteHeader>()
        repeat(2) {
            val favouriteHeader = FavouriteHeader(selectedIcon = null, unselectedIcon = null)

            if (it == 0) {
                favouriteHeader.apply {
                    id = it
                    title = "Events"
                    selectedColor = R.color.purple_900
                    unSelectedColor = R.color.white_900
                    selectedIcon = R.drawable.events
                    unselectedIcon = R.drawable.calender
                }
            } else {
                favouriteHeader.apply {
                    id = it
                    title = "Attractions"
                    selectedColor = R.color.purple_900
                    unSelectedColor = R.color.white_900
                    selectedIcon = R.drawable.attraction
                    unselectedIcon = R.drawable.icon_visited
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
                when(favouriteServices.position){
                    0 -> {
                        //Events Will Be Setup Here
                    }
                    1 -> {
                        //Attractions Will Be Setup Here
                    }
                }
            }
        }
    }

    private fun setupToolbarWithSearchItems() {
        binding.root.apply {
            profilePic.visibility = View.GONE
            img_drawer.visibility = View.GONE
            toolbar_title.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.attractions)
            }
        }
    }


}