package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.databinding.FragmentAttractionDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.viewmodels.AttractionDetailViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_layout_detail.*
import kotlinx.android.synthetic.main.toolbar_layout_detail.view.*


@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>() {
    private val attractionDetailViewModel: AttractionDetailViewModel by viewModels()


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionDetailBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeUiEvents(attractionDetailViewModel)
        attractionDetailViewModel.showLoader(true)
        uiActions()


    }

    private fun uiActions() {

        binding.apply {
            appbarAttractionDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (verticalOffset == -binding.root.collapsingToolbarAttractionDetail.height + binding.root.toolbarAttractionDetail.height) {
                    //toolbar is collapsed here
                    //write your code here
                    defaultCloseToolbar.visibility = View.VISIBLE
                } else {
                    defaultCloseToolbar.visibility = View.GONE
                }
            })
            favourite.setOnClickListener {
                attractionDetailViewModel.showToast("favourite Clicked")
            }
            share.setOnClickListener {
                attractionDetailViewModel.showToast("share Clicked")
            }
            bookingCalender.setOnClickListener {
                attractionDetailViewModel.showToast("bookingCalender Clicked")
            }
            toolbarAttractionDetail.apply {
                favourite.setOnClickListener {
                    attractionDetailViewModel.showToast("favourite Toolbar Clicked")
                }
                share.setOnClickListener {
                    attractionDetailViewModel.showToast("share Toolbar Clicked")
                }
                bookingCalender.setOnClickListener {
                    attractionDetailViewModel.showToast("bookingCalender Toolbar Clicked")
                }
            }


        }
    }

    private fun collapseAppbar(boolean: Boolean = false) {
        binding.appbarAttractionDetail.setExpanded(boolean)
    }


}