package com.dubaiculture.ui.postLogin.explore.adapters.itemcells

import android.content.Context
import android.view.View
import com.dubaiculture.R
import com.dubaiculture.databinding.UpcomingEventsInnerItemCellBinding
import com.dubaiculture.utils.AsyncCell

class UpcomingEventsInnerItemCell(context: Context) : AsyncCell(context,true) {
    var binding: UpcomingEventsInnerItemCellBinding? = null
    override val layoutId = R.layout.upcoming_events_inner_item_cell
    override fun createDataBindingView(view: View): View? {
        binding = UpcomingEventsInnerItemCellBinding.bind(view)
        return view.rootView
    }
}