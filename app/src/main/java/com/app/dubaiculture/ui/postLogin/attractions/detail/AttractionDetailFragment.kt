package com.app.dubaiculture.ui.postLogin.attractions.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.app.dubaiculture.databinding.FragmentAttractionDetailBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar_snippet.view.*


@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>() {
    private var behavior: AppBarLayout.Behavior? = null
    private var coordinatorLayout: CoordinatorLayout.LayoutParams? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentAttractionDetailBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            collapseAppbar()

        },3000)
        Handler(Looper.getMainLooper()).postDelayed({
            expandAppbar()

        },4000)
    }


    private fun collapseAppbar() {
        binding.appbarAttractionDetail.setExpanded(false)
//        coordinatorLayout = binding.appbarAttractionDetail.layoutParams as CoordinatorLayout.LayoutParams?
//        behavior= coordinatorLayout?.behavior as AppBarLayout.Behavior?
//
//        if (behavior != null) {
//            behavior!!.onNestedFling(binding.appCoordinatorAttractionDetail, binding.appbarAttractionDetail, binding.root, 0f, 10000f, true)
//        }
    }

    private fun expandAppbar() {
        binding.appbarAttractionDetail.setExpanded(true)
//        coordinatorLayout = binding.appbarAttractionDetail.layoutParams as CoordinatorLayout.LayoutParams?
//        behavior= coordinatorLayout?.behavior as AppBarLayout.Behavior?
//        if(behavior!=null) {
//      }


    }
}