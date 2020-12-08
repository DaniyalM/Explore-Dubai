package com.app.dubalculture.utils.security.lds

abstract class SecurityManagerLDS {

    val ALIAS: String = "androidKS"
    val PROVIDER: String = "AndroidKeyStore"

    abstract fun encrypt(message: String): String
    abstract fun decrypt(cipherString: String): String
}