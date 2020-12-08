package com.app.dubalculture.utils.security.tls

import timber.log.Timber
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

@Singleton
class TLS @Inject constructor() {
    fun getConfig(): Pair<SSLSocketFactory, X509TrustManager> {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        val caInput: InputStream = BufferedInputStream(FileInputStream("load-der.crt"))
        val ca: X509Certificate = caInput.use {
            cf.generateCertificate(it) as X509Certificate
        }
        Timber.e("ca=%s", ca.subjectDN)

        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        // Create a TrustManager that trusts the CAs inputStream our KeyStore
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
            init(keyStore)
        }
        val trustManager = tmf.trustManagers[0] as X509TrustManager
        // Create an SSLContext that uses our TrustManager
        val context: SSLContext = SSLContext.getInstance("TLS").apply {
            init(null, tmf.trustManagers, null)
        }
        return Pair(context.socketFactory, trustManager)
    }
}