package com.dubaiculture.ui.postLogin.eservices.adapter.listeners

import com.dubaiculture.data.repository.eservices.local.GetFieldValueItem

interface FieldListener {
    fun fetchInput(value: GetFieldValueItem)
    fun dropDownValue(value: GetFieldValueItem)
    fun dateValue(value: GetFieldValueItem)
    fun timeValue(value: GetFieldValueItem)
}
