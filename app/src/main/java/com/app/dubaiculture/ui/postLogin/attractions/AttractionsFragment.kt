package com.app.dubaiculture.ui.postLogin.attractions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentAttractionsBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.adapters.AttractionPagerAdaper
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import kotlinx.android.synthetic.main.attractions_item_cell.view.*
import kotlinx.android.synthetic.main.toolbar_layout.view.*


class AttractionsFragment : BaseFragment<FragmentAttractionsBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionsBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar()
        subscribeUiEvents(attractionViewModel)
        subscribeToObservables()
    }
    @SuppressLint("SetTextI18n")
    private fun setupToolbar(){
        binding.root.profilePic.visibility=View.GONE
        binding.root.toolbar_title.apply {
            visibility=View.VISIBLE
            text= activity.getString(R.string.attractions)
        }

    }

    private fun subscribeToObservables() {
        binding.pager.adapter=AttractionPagerAdaper(this)
        binding.pager.isUserInputEnabled = false
        binding.horizontalSelector.initialize(attractionViewModel.getInterests(),binding.pager)
    }



}