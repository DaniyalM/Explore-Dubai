package com.dubaiculture.ui.postLogin.nearyou

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentSortBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.search.viewmodels.SearchSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortFragment : BaseBottomSheetFragment<FragmentSortBinding>() {
    private val searchViewModel: SearchSharedViewModel by activityViewModels()
    var isaToZ: Boolean = false
    var iszToA: Boolean = false
    var isold: Boolean = false
    var isnew: Boolean = false


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.btnFilter.setOnClickListener {
            dismiss()
            searchViewModel.onDoneClicked(isaToZ,iszToA,isold,isnew)
        }
        binding.aToZ.setOnClickListener {
            searchViewModel.updateAscendSort()
        }
        binding.zToA.setOnClickListener {
            searchViewModel.updateDescendSort()
        }
        binding.old.setOnClickListener {
            searchViewModel.updateOldData()
        }
        binding.newest.setOnClickListener {
            searchViewModel.updateNewData()
        }
        subscribeToObservable()
    }

    private fun subscribeToObservable() {
        searchViewModel.isAtoZ.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    isaToZ = true
                    binding.aToZ.isClickable = false
                    binding.aToZ.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.aToZ.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                } else {
                    isaToZ = false
                    binding.aToZ.isClickable = true
                    binding.aToZ.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.aToZ.setTextColor(ContextCompat.getColor(activity, R.color.black_750))
                }
            }
        }
        searchViewModel.isZtoA.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    iszToA = true
                    binding.zToA.isClickable = false
                    binding.zToA.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.zToA.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                } else {
                    iszToA = false
                    binding.zToA.isClickable = true
                    binding.zToA.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.zToA.setTextColor(ContextCompat.getColor(activity, R.color.black_750))
                }
            }
        }
        searchViewModel.isNew.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    isnew = true
                    binding.newest.isClickable = false
                    binding.newest.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.newest.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                } else {
                    isnew = false
                    binding.newest.isClickable = true
                    binding.newest.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.newest.setTextColor(ContextCompat.getColor(activity, R.color.black_750))
                }
            }
        }
        searchViewModel.isOld.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    isold = true
                    binding.old.isClickable = false
                    binding.old.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.old.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                } else {
                    isold = false
                    binding.old.isClickable = true
                    binding.old.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.old.setTextColor(ContextCompat.getColor(activity, R.color.black_750))
                }
            }
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSortBinding.inflate(inflater, container, false)

}