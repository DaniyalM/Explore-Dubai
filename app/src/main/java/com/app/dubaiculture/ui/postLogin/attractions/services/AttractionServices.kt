package com.app.dubaiculture.ui.postLogin.attractions.services

sealed class AttractionServices {
    class CategoryClick(val position:Int) : AttractionServices()
}