package com.app.dubaiculture.ui.postLogin.popular_service.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentServiceDetailFragmentBinding
import com.app.dubaiculture.databinding.ItemLayoutTabHeaderBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.app.dubaiculture.ui.postLogin.popular_service.adapter.PopularServiceListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

//@AndroidEntryPoint
class ServiceDetailFragment : BaseFragment<FragmentServiceDetailFragmentBinding>() {

    private var headerTabsAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()
    private var linearLayoutManger: LinearLayoutManager? = null

    companion object {
        var headerSelectionFlag: Int = 0
        var headerPreviousSelectionFlag: Int = 0
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentServiceDetailFragmentBinding.inflate(inflater, container, false)

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

//        tabInit()

    }

    private fun updateTabs(){}
    private fun tabSetter() {
        headerTabsAdapter.clear()
        TabHeaders.values().forEach {
            headerTabsAdapter.add(
                PopularServiceListItem<ItemLayoutTabHeaderBinding>(
                    rowClickListener = object : RowClickListener {
                        override fun rowClickListener(position: Int) {
                            headerPreviousSelectionFlag= headerSelectionFlag
                            headerSelectionFlag = position
                            tabSetter()
                        }

                        override fun rowClickListener(position: Int, imageView: ImageView) {
                            TODO("Not yet implemented")
                        }
                    },
                    resLayout = R.layout.item_layout_tab_header,
                    tabHeader = it,
                    context = activity
                )
            )
        }
    }
}