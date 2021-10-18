package com.app.dubaiculture.ui.postLogin.profile.service

sealed class ImagePickerService{
    object CameraClick: ImagePickerService()
    object GalleryClick: ImagePickerService()
}