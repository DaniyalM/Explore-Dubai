package com.dubaiculture.data.repository.base

open class EServiceBaseResponse() {
    val error: List<Error> = emptyList()
    val message: String = ""
    val statusCode: Int = 0
    val success: Boolean = false
}

data class Error(
    val code: String,
    val message: String
)