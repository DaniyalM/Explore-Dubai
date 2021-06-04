package com.app.dubaiculture.ui.postLogin.more.profile.service

sealed class ImagePickerService{
    object CameraClick: ImagePickerService()
    object GalleryClick: ImagePickerService()
}