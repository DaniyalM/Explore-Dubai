package com.dubaiculture.ui.postLogin.nearyou

import android.content.res.TypedArray
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.dubaiculture.R
import com.dubaiculture.databinding.FragmentSortBinding
import com.dubaiculture.ui.base.BaseBottomSheetFragment
import com.dubaiculture.ui.postLogin.search.viewmodels.SearchSharedViewModel
import com.dubaiculture.utils.ColorUtil
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
            searchViewModel.onDoneClicked(isaToZ, iszToA, isold, isnew)
        }
        binding.aToZ.setOnClickListener {
//            searchViewModel.updateAscendSort()
            isaToZ = true
            iszToA = false
            isnew = false
            isold = false

            binding.aToZ.isClickable = false
            binding.aToZ.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
            binding.aToZ.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

            binding.zToA.isClickable = true
            binding.zToA.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.zToA.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.newest.isClickable = true
            binding.newest.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.newest.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.old.isClickable = true
            binding.old.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.old.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

        }
        binding.zToA.setOnClickListener {
//            searchViewModel.updateDescendSort()
            iszToA = true
            isaToZ = false
            isnew = false
            isold = false
            binding.zToA.isClickable = false
            binding.zToA.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
            binding.zToA.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

            binding.newest.isClickable = true
            binding.newest.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.newest.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.old.isClickable = true
            binding.old.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.old.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.aToZ.isClickable = true
            binding.aToZ.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.aToZ.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))
        }
        binding.old.setOnClickListener {
//            searchViewModel.updateOldData()
            isold = true
            isnew = false
            iszToA = false
            isaToZ = false
            binding.old.isClickable = false
            binding.old.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
            binding.old.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

            binding.aToZ.isClickable = true
            binding.aToZ.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.aToZ.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.zToA.isClickable = true
            binding.zToA.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.zToA.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.newest.isClickable = true
            binding.newest.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.newest.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

        }
        binding.newest.setOnClickListener {
//            searchViewModel.updateNewData()
            isnew = true
            iszToA = false
            isaToZ = false
            isold = false
            binding.newest.isClickable = false
            binding.newest.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
            binding.newest.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

            binding.old.isClickable = true
            binding.old.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.old.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.aToZ.isClickable = true
            binding.aToZ.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.aToZ.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

            binding.zToA.isClickable = true
            binding.zToA.background =
                ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
            binding.zToA.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))
        }
        subscribeToObservable()
    }

//    private fun ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary): Int {
//        val typedValue = TypedValue()
//        val a: TypedArray =
//            requireContext().obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary))
//        val color = a.getColor(0, 0)
//        a.recycle()
//        return color
//    }

    private fun subscribeToObservable() {
        searchViewModel.isAtoZ.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    isaToZ = true
                    iszToA = false
                    isnew = false
                    isold = false

                    binding.aToZ.isClickable = false
                    binding.aToZ.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.aToZ.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                    binding.zToA.isClickable = true
                    binding.zToA.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.zToA.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.newest.isClickable = true
                    binding.newest.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.newest.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.old.isClickable = true
                    binding.old.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.old.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                }
//                else {
//                    isaToZ = false
//                    iszToA = false
//                    isnew = false
//                    isold = false
//                    binding.aToZ.isClickable = true
//                    binding.aToZ.background =
//                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
//                    binding.aToZ.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))
//                }
            }
        }
        searchViewModel.isZtoA.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    iszToA = true
                    isaToZ = false
                    isnew = false
                    isold = false
                    binding.zToA.isClickable = false
                    binding.zToA.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.zToA.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                    binding.newest.isClickable = true
                    binding.newest.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.newest.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.old.isClickable = true
                    binding.old.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.old.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.aToZ.isClickable = true
                    binding.aToZ.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.aToZ.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                }
//                else {
//                    iszToA = false
//                    isaToZ = false
//                    isnew = false
//                    isold = false
//                    binding.zToA.isClickable = true
//                    binding.zToA.background =
//                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
//                    binding.zToA.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))
//                }
            }
        }
        searchViewModel.isNew.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    isnew = true
                    iszToA = false
                    isaToZ = false
                    isold = false
                    binding.newest.isClickable = false
                    binding.newest.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.newest.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                    binding.old.isClickable = true
                    binding.old.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.old.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.aToZ.isClickable = true
                    binding.aToZ.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.aToZ.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.zToA.isClickable = true
                    binding.zToA.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.zToA.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))


                }
//                else {
//                    isnew = false
//
//                    binding.newest.isClickable = true
//                    binding.newest.background =
//                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
//                    binding.newest.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))
//                }
            }
        }
        searchViewModel.isOld.observe(viewLifecycleOwner) {
            it?.peekContent()?.let {
                if (it) {
                    isold = true
                    isnew = false
                    iszToA = false
                    isaToZ = false
                    binding.old.isClickable = false
                    binding.old.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_selected)
                    binding.old.setTextColor(ContextCompat.getColor(activity, R.color.white_900))

                    binding.aToZ.isClickable = true
                    binding.aToZ.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.aToZ.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.zToA.isClickable = true
                    binding.zToA.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.zToA.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                    binding.newest.isClickable = true
                    binding.newest.background =
                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
                    binding.newest.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))

                }
//                else {
//                    isold = false
//                    binding.old.isClickable = true
//                    binding.old.background =
//                        ContextCompat.getDrawable(activity, R.drawable.sort_box_shape)
//                    binding.old.setTextColor(ColorUtil.fetchColor(requireContext(),R.attr.colorPrimary))
//                }
            }
        }
    }


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSortBinding.inflate(inflater, container, false)

}