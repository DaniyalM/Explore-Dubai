package com.app.dubaiculture.data

import okhttp3.ResponseBody

// used among repositories, interactors and viewmodels so it shouldn't be a part of data package
sealed class Result<out R> {
    data class Success<out T>(val value: T, val message: String = "Request Success") : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Failure(
        val isNetWorkError:Boolean,
        val errorCode:Int?,
        val errorBody:ResponseBody?
    ):Result<Nothing>()
}