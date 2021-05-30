package com.app.dubaiculture.ui.postLogin.more.profile.service

//class ImagePicker {
//    inner class cameraClick(val clickFlag: Boolean)
//}

sealed class ImagePickerService{
    class cameraClick(val clickFlag: Boolean): ImagePickerService()
    class galleryClick(val clickFlag: Boolean): ImagePickerService()
}