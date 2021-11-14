package com.dubaiculture.ui.postLogin.attractions.services

sealed class AttractionServices {
    class CategoryClick(val position: Int) : AttractionServices()
    class TriggerRefresh() : AttractionServices()
}