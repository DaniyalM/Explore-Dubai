package com.dubaiculture.ui.postLogin.popular_service.detail.pages.dialogs

sealed class ClickBack {
     class doBack(val isBack: Boolean = false) : ClickBack()
}
