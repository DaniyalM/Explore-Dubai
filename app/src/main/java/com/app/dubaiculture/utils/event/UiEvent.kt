package com.app.dubaiculture.utils.event

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.navigation.NavDirections

sealed class UiEvent {
    class ShowLoader(val show: Boolean) : UiEvent()
    class ShowToast(val message: String) : UiEvent()
    class ShowSnackbar(val message: String, val action: (() -> Unit)? = null) : UiEvent()
    class ShowAlert(val title: String, val message: String) : UiEvent()
    class ShowErrorDialog(val title:String,val message:String,@ColorRes val colorBg : Int):UiEvent()
    class NavigateByAction(val actionId: Int, val bundle: Bundle? = null) : UiEvent()
    class NavigateByDirections(val navDirections: NavDirections) : UiEvent()
    class NavigateByBack() : UiEvent()

}