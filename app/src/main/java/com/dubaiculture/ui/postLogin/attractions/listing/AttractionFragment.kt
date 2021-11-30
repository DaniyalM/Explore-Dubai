package com.dubaiculture.ui.postLogin.attractions.listing

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.dubaiculture.R
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.local.models.AttractionCategory
import com.dubaiculture.databinding.FragmentAttractionHeaderBinding
import com.dubaiculture.databinding.LayoutTabAttractionHeaderItemBinding
import com.dubaiculture.ui.base.BaseFragment
import com.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import com.dubaiculture.utils.getColorFromAttr
import com.dubaiculture.utils.glideInstance
import com.dubaiculture.utils.handleApiError
import com.dubaiculture.utils.hide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AttractionFragment : BaseFragment<FragmentAttractionHeaderBinding>() {

    private var list: List<AttractionCategory>? = null
    private lateinit var attractionPagerAdaper: AttractionPagerAdaper
    private val attractionNavArgs: AttractionFragmentArgs by navArgs()

    private var isNavigated: Boolean = false

    private val attractionViewModel: AttractionViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAttractionHeaderBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarWithSearchItems()
        initViewPager()
        refreshRequest()
        binding.toolbarSnippet.toolbarLayout.search.hide()
//        binding.toolbarSnippet.toolbarLayout.search.setOnClickListener {
//            navigateByDirections(AttractionFragmentDirections.actionAttractionsFragmentToSearchNavigation())
//        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        subscribeUiEvents(attractionViewModel)

        subscribeToObservable()


    }

    private fun subscribeToObservable() {
        attractionViewModel.attractionCategoryList.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    it.let { result ->
                        list = result.value
                        attractionPagerAdaper.list = result.value
                        if (attractionNavArgs.isExplore && !isNavigated) {
                            isNavigated = true
                            if (attractionNavArgs.positionId > 0) {
                                binding.pager.currentItem = attractionNavArgs.positionId
                            }
                        }


                    }
                }
                is Result.Failure -> {
                    handleApiError(it, attractionViewModel)
                }
            }
        }

    }
    private fun setupToolbarWithSearchItems() {

        binding.root.apply {

            binding.toolbarSnippet.toolbarLayout.profilePic.visibility = View.GONE
            binding.toolbarSnippet.toolbarLayout.imgDrawer.visibility = View.GONE
            binding.toolbarSnippet.toolbarLayout.toolbarTitle.apply {
                visibility = View.VISIBLE
                text = activity.getString(R.string.attractions)
            }
        }
    }

    private fun refreshRequest() {

        binding.swipeRefresh.apply {
            setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
            )
            setOnRefreshListener {
                binding.swipeRefresh.isRefreshing = false
                attractionViewModel.refreshList()

            }

        }
    }


    private fun initViewPager() {
        if (!this::attractionPagerAdaper.isInitialized){
            attractionPagerAdaper = AttractionPagerAdaper(this)
            binding.pager.adapter = attractionPagerAdaper

            binding.pager.isUserInputEnabled = false
            binding.pager.isSaveEnabled=false
            TabLayoutMediator(
                binding.attractionTabs, binding.pager
            ) { tab: TabLayout.Tab, position: Int ->
                position.run {
                    list?.get(position)?.let {
                        val v: LayoutTabAttractionHeaderItemBinding =
                            LayoutTabAttractionHeaderItemBinding.inflate(layoutInflater)
                        v.tvTitle.text = it.title
                        val tabColor = if (it.color.isNullOrEmpty()) "#5d2c83" else it.color
                        val tabSelected = it.selectedSvg
                        val tabUnSelected = it.icon

                        when (position) {
                            0 -> {

                                if (binding.pager.currentItem == position) {
                                    v.cardview.setCardBackgroundColor(Color.parseColor(tabColor!!))
                                    v.root.glideInstance(tabSelected, true).into(v.imgInnerIcon)
                                    v.imgInnerIcon.setColorFilter(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.white_900
                                        )
                                    )

                                    v.tvTitle.setTextColor(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.white_900
                                        )
                                    )
                                    tab.customView = v.root
                                } else {
                                    v.cardview.setCardBackgroundColor(activity.getColorFromAttr(R.attr.colorOnSecondary))
                                    v.root.glideInstance(tabUnSelected, true).into(v.imgInnerIcon)
                                    v.imgInnerIcon.setColorFilter(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.purple_900
                                        )
                                    )

                                    v.tvTitle.setTextColor(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.gray_700
                                        )
                                    )
                                    tab.customView = v.root
                                }


                            }
                            else -> {
                                if (binding.pager.currentItem == position) {
                                    tabSelected(tab)
                                    v.cardview.setCardBackgroundColor(Color.parseColor(tabColor!!))
                                    v.root.glideInstance(tabSelected, true).into(v.imgInnerIcon)
                                    v.imgInnerIcon.setColorFilter(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.white_900
                                        )
                                    )

                                    v.tvTitle.setTextColor(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.white_900
                                        )
                                    )
                                    tab.customView = v.root
                                } else {
                                    v.cardview.setCardBackgroundColor(activity.getColorFromAttr(R.attr.colorOnSecondary))
                                    v.root.glideInstance(tabUnSelected, true).into(v.imgInnerIcon)
                                    v.imgInnerIcon.setColorFilter(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.purple_900
                                        )
                                    )

                                    v.tvTitle.setTextColor(
                                        ContextCompat.getColor(
                                            activity,
                                            R.color.gray_700
                                        )
                                    )
                                    tab.customView = v.root
                                }
                            }
                        }
                    }
                }


            }.attach()
            binding.attractionTabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        tabSelected(it)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.let {
//                    tempTab = it
                        tabUnSelected(tab)
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

        }


    }

    private fun tabSelected(tab: TabLayout.Tab) {
        tab.customView?.apply {
            list?.get(tab.position)?.let {
                val view = this
                val title = view.findViewById<TextView>(R.id.tv_title)
                val icon = view.findViewById<AppCompatImageView>(R.id.imgInnerIcon)
                val card = view.findViewById<MaterialCardView>(R.id.cardview)
                title?.text = it.title
                val tabColor = if (it.color.isNullOrEmpty()) "#5d2c83" else it.color

                card.setCardBackgroundColor(Color.parseColor(tabColor))
                view.glideInstance(it.selectedSvg, true).into(icon)
                icon.setColorFilter(ContextCompat.getColor(activity, R.color.white_900))

                title.setTextColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.white_900
                    )
                )
                tab.customView = view

            }

        }

    }

    private fun tabUnSelected(tab: TabLayout.Tab) {
        tab.customView?.apply {
            list?.get(tab.position)?.let {
                val view = this
                val title = view.findViewById<TextView>(R.id.tv_title)
                val icon = view.findViewById<AppCompatImageView>(R.id.imgInnerIcon)
                val card = view.findViewById<MaterialCardView>(R.id.cardview)
                title?.text = it.title

                card.setCardBackgroundColor(activity.getColorFromAttr(R.attr.colorOnSecondary))
                view.glideInstance(it.icon, true).into(icon)
                icon.setColorFilter(ContextCompat.getColor(activity, R.color.purple_900))

                title.setTextColor(
                    ContextCompat.getColor(
                        activity,
                        R.color.gray_700
                    )
                )
                tab.customView = view

            }

        }

    }
}