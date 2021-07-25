package com.app.dubaiculture.ui.postLogin.popular_service.adapter

import androidx.databinding.ViewDataBinding
import com.app.dubaiculture.R
import com.app.dubaiculture.data.repository.popular_service.local.models.Description
import com.app.dubaiculture.data.repository.popular_service.local.models.Procedure
import com.app.dubaiculture.databinding.ItemsServiceDetailDescLayoutBinding
import com.app.dubaiculture.databinding.ItemsServiceDetailProcedureLayoutBinding
import com.app.dubaiculture.ui.postLogin.events.`interface`.RowClickListener
import com.xwray.groupie.databinding.BindableItem

class ServiceDetailListingItems<T : ViewDataBinding, out D>(
    private val rowClickListener: RowClickListener? = null,
    val eService: D? = null,
    val resLayout: Int = R.layout.items_service_detail_desc_layout,
) : BindableItem<T>() {
    override fun bind(viewBinding: T, position: Int) {
        when (viewBinding) {
            is ItemsServiceDetailDescLayoutBinding -> {
                if (eService is Description) {
                    viewBinding.description = eService
                }
            }

            is ItemsServiceDetailProcedureLayoutBinding -> {
                if (eService is Procedure) {
                    viewBinding.procedure = eService
                }
            }
        }


    }

    override fun getLayout() = resLayout

}