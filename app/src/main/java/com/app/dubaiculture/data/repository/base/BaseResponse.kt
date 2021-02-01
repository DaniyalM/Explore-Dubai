package com.app.dubaiculture.data.repository.base

open class BaseResponse() {
    val statusCode: Int = 0
    var status: String? = ""
    var transactionStatus: Boolean = false
    var errorMessage: String? = ""
    var errorMessages: List<String> = listOf()
    var message: String? = ""
    var transactionStatusCode: Int? = 0
    var successful: Boolean = false
}