package com.app.dubaiculture.ui.postLogin.attractions

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.app.dubaiculture.databinding.AttractionListItemCellBinding
import com.app.dubaiculture.databinding.FragmentAttractionListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionListItem
import com.app.dubaiculture.ui.postLogin.attractions.services.AttractionServices
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.app.dubaiculture.ui.postLogin.events.`interface`.FavouriteChecker
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.handleApiError
import com.squareup.otto.Subscribe
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AttractionListingFragment : BaseFragment<FragmentAttractionListingBinding>() {
    private lateinit var linearLayoutManger: LinearLayoutManager
    private val attractionViewModel: AttractionViewModel by viewModels()
    private lateinit var attractionCat: AttractionCategory
    private var pageNumber: Int = 1
    private var pageSize: Int = 3
    var contentLoaded = false
    private var lastFirstVisiblePosition: Int = 0
    private var attractionListingAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    var contentLoadMore = true
    override fun onPause() {
        super.onPause()
        lastFirstVisiblePosition =
            (binding.rvAttractionListing.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

    }

    override fun onResume() {
        super.onResume()
        try {
            binding.rvAttractionListing.smoothScrollToPosition(lastFirstVisiblePosition)
        } catch (ex: IllegalArgumentException) {
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(attractionCat: AttractionCategory) = AttractionListingFragment().apply {
            arguments = Bundle().apply {
                putParcelable(Constants.NavBundles.ATTRACTION_CAT_OBJECT, attractionCat)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUiEvents(attractionViewModel)
        initRecyclerView()
        callingObservables()
        subscribeToObservables()
    }

    private fun callingObservables() {

        if (!contentLoaded) {
            binding.progressBar.visibility = View.VISIBLE

            attractionCat.id?.let {

                attractionViewModel.getAttractionThroughCategory(
                    it,
                    pageNumber,
                    pageSize,
                    getCurrentLanguage().language
                )
                contentLoaded = true
            }

        }

    }


    private fun subscribeToObservables() {
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
        attractionViewModel.attractionList.observe(viewLifecycleOwner) { it ->

            when (it) {

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    contentLoadMore = true
                    if (pageNumber == 1) {
                        if (attractionListingAdapter.itemCount > 0) {
                            attractionListingAdapter.clear()
                        }
                        attractionListingAdapter.apply {
                            it.value.forEach {
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
                                                    R.id.action_attractionsFragment_to_postLoginFragment,
                                                    itemId, attractionViewModel,
                                                    1
                                                )
                                            }
                                        },
                                        rowClickListener = object : RowClickListener {
                                            override fun rowClickListener(position: Int) {


                                            }

                                            override fun rowClickListener(
                                                position: Int,
                                                imageView: ImageView
                                            ) {
                                                imageView.transitionName = it.id
                                                val extras = FragmentNavigatorExtras(
                                                    imageView to it.id
                                                )

                                                navigate(
                                                    R.id.action_attractionsFragment_to_attractionDetailFragment,
                                                    Bundle().apply {
                                                        putParcelable(
                                                            Constants.NavBundles.ATTRACTION_OBJECT,
                                                            it
                                                        )
                                                    },
                                                    extras = extras

                                                )
                                            }
                                        },
                                        attraction = it,
                                        context = activity

                                    )
                                )
                            }
                        }

                    } else {
                        if (it.value.isEmpty()) {
                            pageNumber -= 1
                        } else {
                            attractionListingAdapter.apply {
                                it.value.forEach {
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
                                                        R.id.action_attractionsFragment_to_postLoginFragment,
                                                        itemId, attractionViewModel,
                                                        1
                                                    )
                                                }

                                            },
                                            rowClickListener = object : RowClickListener {
                                                override fun rowClickListener(position: Int) {

                                                }

                                                override fun rowClickListener(
                                                    position: Int,
                                                    imageView: ImageView
                                                ) {
                                                    imageView.transitionName = it.id
                                                    val extras = FragmentNavigatorExtras(
                                                        imageView to it.id
                                                    )

                                                    navigate(
                                                        R.id.action_attractionsFragment_to_attractionDetailFragment,
                                                        Bundle().apply {
                                                            putParcelable(
                                                                Constants.NavBundles.ATTRACTION_OBJECT,
                                                                it
                                                            )
                                                        },
                                                        extras = extras
                                                    )
                                                }
                                            },
                                            attraction = it,
                                            context = activity
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE

                    handleApiError(it, attractionViewModel)
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE

                }

            }

        }
    }


    private fun initRecyclerView() {

        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int
        linearLayoutManger = LinearLayoutManager(activity)
        binding.rvAttractionListing.apply {
            layoutManager = linearLayoutManger
            adapter = attractionListingAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) { //check for scroll down
                        (layoutManager as LinearLayoutManager).apply {
                            visibleItemCount = this.childCount
                            totalItemCount = this.itemCount
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
            })
        }

    }


    @Subscribe
    fun handlingRefresh(attractionServices: AttractionServices) {
        when (attractionServices) {
            is AttractionServices.TriggerRefresh -> {
                if (attractionListingAdapter.itemCount > 0) {
                    attractionListingAdapter.clear()
                    contentLoaded = false
                    pageNumber = 1
                    callingObservables()
                }

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.apply {
            attractionCat = getParcelable(Constants.NavBundles.ATTRACTION_CAT_OBJECT)!!
        }
    }
}