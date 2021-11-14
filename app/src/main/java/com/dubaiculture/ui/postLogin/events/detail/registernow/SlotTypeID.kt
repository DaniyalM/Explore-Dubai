package com.dubaiculture.ui.postLogin.events.detail.registernow

data class SlotTypeID(
        val slotId: String,
        val time: String
){
    override fun toString(): String {
        return time
    }
}