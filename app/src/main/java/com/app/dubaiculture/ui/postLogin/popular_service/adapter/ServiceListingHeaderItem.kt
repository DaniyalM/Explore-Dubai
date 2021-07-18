package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.AttractionTitleListItemBinding
import com.app.dubaiculture.ui.postLogin.attractions.clicklisteners.TabsHeaderClick
import com.xwray.groupie.databinding.BindableItem

class ServiceListingHeaderItem<T>(
    val displayValue: String,
    val data: T,
    var isSelected: Boolean = false,
    private val selectedInnerImg: String? = null,
    private val unSelectedInnerImg: String? = null,
    private val progressListener: TabsHeaderClick? = null,
    private val colorBg: String? = null,
    private val resLayout: Int = R.layout.attraction_title_list_item
) : BindableItem<AttractionTitleListItemBinding>() {



    override fun getLayout() = resLayout
    override fun bind(viewBinding: AttractionTitleListItemBinding, position: Int) {
    }
}