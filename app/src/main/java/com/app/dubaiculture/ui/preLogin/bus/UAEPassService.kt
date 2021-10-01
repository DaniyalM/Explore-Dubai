package com.app.dubaiculture.ui.preLogin.bus

sealed class UAEPassService {
    class UaeClick(val trigger: Boolean = false) : UAEPassService()
}
