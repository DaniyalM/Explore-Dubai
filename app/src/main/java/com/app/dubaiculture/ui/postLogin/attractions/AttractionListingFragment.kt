package com.app.dubaiculture.ui.postLogin.attractions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.app.dubaiculture.databinding.AttractionTitleListItemBinding
import com.app.dubaiculture.databinding.FragmentAttractionListingBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.viewmodels.AttractionViewModel
import kotlinx.android.synthetic.main.fragment_attraction_listing.view.*

class AttractionListingFragment : BaseFragment<FragmentAttractionListingBinding>() {
    private val attractionViewModel: AttractionViewModel by viewModels()

    private var attractionCategoryTag: String = ""


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionListingBinding.inflate(inflater, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.fragName.text=attractionCategoryTag

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(ATTRACTION_CATEG0RY_TYPE)?.let {
            attractionCategoryTag=it
        }

    }

    companion object {

        var ATTRACTION_CATEG0RY_TYPE: String = "AttractionsCategory"

        @JvmStatic
        fun newInstance(type: String) = AttractionListingFragment().apply {
            arguments = Bundle().apply {
                putString(ATTRACTION_CATEG0RY_TYPE, type)
            }
        }
    }

}