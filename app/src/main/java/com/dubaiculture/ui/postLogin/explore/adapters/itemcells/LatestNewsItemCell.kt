package com.dubaiculture.ui.postLogin.explore.adapters.itemcells

import android.content.Context
import android.view.View
import com.dubaiculture.R
import com.dubaiculture.databinding.SectionItemContainerCellBinding
import com.dubaiculture.utils.AsyncCell

class LatestNewsItemCell(context: Context) : AsyncCell(context) {
    var binding: SectionItemContainerCellBinding? = null
    override val layoutId = R.layout.section_item_container_cell
    override fun createDataBindingView(view: View): View? {
        binding = SectionItemContainerCellBinding.bind(view)
        return view.rootView
    }
}
