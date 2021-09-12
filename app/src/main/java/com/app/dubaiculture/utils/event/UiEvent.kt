package com.app.dubaiculture.utils.event

import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.app.dubaiculture.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

sealed class UiEvent {
    class ShowLoader(val show: Boolean) : UiEvent()
    class ShowToast(val message: String) : UiEvent()
    class ShowSnackbar(val message: String, val action: (() -> Unit)? = null) : UiEvent()
    class ShowAlert(
        val title: String = Constants.Alert.DEFAULT_TITLE,
        val message: String,
        val textPositive: String = Constants.Alert.DEFAULT_TEXT_POSITIVE,
        val textNegative: String? = null,
        val actionPositive: (() -> Unit)? = null
    ) : UiEvent()
    class ShowErrorDialog(val title:String,val message:String,@ColorRes val colorBg : Int):UiEvent()
    class NavigateByAction(val actionId: Int, val bundle: Bundle? = null) : UiEvent()
    class NavigateByDirections(val navDirections: NavDirections) : UiEvent()
    class NavigateByBack() : UiEvent()
    class NavigateByActionNavOption(val actionId: Int, val bundle: Bundle? = null,val navOptions: NavOptions) : UiEvent()
    class ShowBottomSheet(
        val bottomSheetFragment: BottomSheetDialogFragment,
        val tag: String? = null
    ) : UiEvent()

}