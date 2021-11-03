package com.app.dubaiculture.ui.postLogin.attractions

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.databinding.FragmentAttractionListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionInnerAdapter
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.AttractionClickListener
import com.app.dubaiculture.ui.postLogin.attractions.listing.AttractionFragment
import com.app.dubaiculture.ui.postLogin.attractions.listing.AttractionFragmentDirections
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionListingViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AttractionListingFragment : BaseFragment<FragmentAttractionListingBinding>() {

    private lateinit var attractionInnerAdapter: AttractionInnerAdapter
    private val attractionViewModel: AttractionListingViewModel by viewModels()


    companion object {

        @JvmStatic
        fun newInstance(attractionCat: AttractionCategory): AttractionListingFragment {
            val args = Bundle()
            args.putParcelable(
                Constants.NavBundles.ATTRACTION_CAT_OBJECT,
                attractionCat
            )
            val fragment = AttractionListingFragment()
            fragment.arguments = args

            return fragment
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionListingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(attractionViewModel)
        subscribeToObservables()

    }

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
        attractionViewModel.attractionPagination.observe(viewLifecycleOwner) { it ->
            lifecycleScope.launch {
                attractionInnerAdapter.submitData(it)
            }

        }
    }

    private fun initRecyclerView() {
        if (!this::attractionInnerAdapter.isInitialized) {
            binding.rvAttractionListing.apply {
                layoutManager = LinearLayoutManager(activity)

                attractionInnerAdapter = AttractionInnerAdapter(
                    favChecker = object : FavouriteChecker {
                        override fun checkFavListener(
                            checkbox: CheckBox,
                            pos: Int,
                            isFav: Boolean,
                            itemId: String
                        ) {
                            favouriteClick(
                                checkbox,
                                isFav,
                                R.id.action_attractionsFragment_to_postLoginFragment,
                                itemId, attractionViewModel,
                                1
                            )

                        }
                    },
                    attractionClickListener = object : AttractionClickListener {
                        override fun rowClickListener(attraction: Attractions) {
                        }

                        override fun rowClickListener(
                            position: Int,
                            imageView: ImageView,
                            attraction: Attractions
                        ) {
                            imageView.transitionName = attraction.id
                            (parentFragment as AttractionFragment).navigateByDirections(
                                AttractionFragmentDirections.actionAttractionsFragmentToAttractionDetailFragment(
                                    attraction
                                )
                            )
                        }
                    }
                )


                adapter = attractionInnerAdapter

            }
        }


    }


}