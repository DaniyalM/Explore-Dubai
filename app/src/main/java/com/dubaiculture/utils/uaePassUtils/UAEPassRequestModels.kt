package com.dubaiculture.utils.uaePassUtils

import ae.sdg.libraryuaepass.business.Environment
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel
import ae.sdg.libraryuaepass.business.documentsigning.model.DocumentSigningRequestParams
import ae.sdg.libraryuaepass.business.documentsigning.model.UAEPassDocumentDownloadRequestModel
import ae.sdg.libraryuaepass.business.documentsigning.model.UAEPassDocumentSigningRequestModel
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestByAccessTokenModel
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestModel
import ae.sdg.libraryuaepass.utils.Utils.generateRandomString
import android.content.Context
import android.content.pm.PackageManager
import com.dubaiculture.BuildConfig
import java.io.File
import java.util.*

class UAEPassRequestModels {


    //UAE PASS START -- ADD BELOW FIELDS
    private val UAE_PASS_CLIENT_ID = "dcaa_app_stage"
    private val UAE_PASS_CLIENT_SECRET = "QyFfkEGfpwvSbSqc"

    //    private val REDIRECT_URL: String = "https://qa-id.uaepass.ae/idshub/authorize?redirect_uri=dc://com.dc.dc-int&client_id=${UAE_PASS_CLIENT_ID}&response_type=code&state=ShNP22hyl1jUU2RGjTRkpg==&scope=urn:uae:digitalid:profile:general&acr_values=urn:safelayer:tws:policies:authentication:level:low&ui_locales=en"
    private val UAE_PASS_ENVIRONMENT: Environment = Environment.STAGING

    //UAE PASS END -- ADD BELOW FIELDS
    private val REDIRECT_3: String =
        "https://qa-id.uaepass.ae/idshub/authorize?response_type=code&client_id=dcaa_app_stage&scope=urn:uae:digitalid:profile:general&state=HnlHOJTkTb66Y5H&redirect_uri=dc://com.dc.dc-int&acr_values=urn:digitalid:authentication:flow:mobileondevice"
    private val REDIRECT_URL_2: String =
        "https://qa-id.uaepass.ae/idshub/authorize?response_type=code&client_id=dcaa_app_stage&scope=urn:uae:digitalid:profile:general&state=HnlHOJTkTb66Y5H&redirect_uri=dc://com.dc.dc-int&acr_values=urn:safelayer:tws:policies:authentication:flow:mobileondevice"

    //UAE PASS END -- ADD BELOW FIELDS
    private val REDIRECT_URL: String = "dc://com.dc.dc-int"

    private val DOCUMENT_SIGNING_SCOPE = "urn:safelayer:eidas:sign:process:document"
    private val RESPONSE_TYPE = "code"
    private val SCOPE = "urn:uae:digitalid:profile"
    private val ACR_VALUES_MOBILE = "urn:digitalid:authentication:flow:mobileondevice"
    private val ACR_VALUES_WEB = "urn:safelayer:tws:policies:authentication:level:low"
    private val UAE_PASS_PACKAGE_ID = "ae.uaepass.mainapp"
    private val UAE_PASS_QA_PACKAGE_ID = "ae.uaepass.mainapp.qa"

    private val SCHEME: String = BuildConfig.URI_SCHEME
    private val FAILURE_HOST: String = BuildConfig.URI_HOST_FAILURE
    private val SUCCESS_HOST: String = BuildConfig.URI_HOST_SUCCESS
    private val STATE = generateRandomString(24)

    var MY_SCOPE = "urn:uae:digitalid:profile:general"
    var MY_STATE = "HnlHOJTkTb66Y5H"
//    var MY_acr_values = "urn:digitalid:authentication:flow:mobileondevice"


    private fun isPackageInstalled(packageManager: PackageManager): Boolean {
        var packageName: String? = null
        if (UAE_PASS_ENVIRONMENT == Environment.PRODUCTION) {
            packageName = UAE_PASS_PACKAGE_ID
        } else if (UAE_PASS_ENVIRONMENT == Environment.STAGING) {
            packageName = UAE_PASS_QA_PACKAGE_ID
        }
        var found = true
        try {
            packageManager.getPackageInfo(packageName!!, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            found = false
        }
        return found
    }

    fun getUAEPassHavingAccessToken(accessToken: String): UAEPassProfileRequestByAccessTokenModel {
        return UAEPassProfileRequestByAccessTokenModel(
            UAE_PASS_ENVIRONMENT,  // done
            UAE_PASS_CLIENT_ID,
            accessToken = accessToken,
            MY_STATE
        )
    }


    fun getAuthenticationRequestModel(context: Context): UAEPassAccessTokenRequestModel? {
        var ACR_VALUE = ""
        ACR_VALUE = if (isPackageInstalled(context.packageManager)) {
            ACR_VALUES_MOBILE
        } else {
            ACR_VALUES_WEB
        }
        return UAEPassAccessTokenRequestModel(
            UAE_PASS_ENVIRONMENT,
            UAE_PASS_CLIENT_ID,
            UAE_PASS_CLIENT_SECRET,
            SCHEME,
            FAILURE_HOST,
            SUCCESS_HOST,
            REDIRECT_URL,
            MY_SCOPE,
            RESPONSE_TYPE,
            ACR_VALUE,
            MY_STATE
        )
    }

    fun getDocumentRequestModel(
        file: File?,
        documentSigningParams: DocumentSigningRequestParams
    ): UAEPassDocumentSigningRequestModel? {
        return UAEPassDocumentSigningRequestModel(
            UAE_PASS_ENVIRONMENT,
            UAE_PASS_CLIENT_ID,
            UAE_PASS_CLIENT_SECRET,
            SCHEME,
            FAILURE_HOST,
            SUCCESS_HOST,
            Objects.requireNonNull(documentSigningParams.finishCallbackUrl),
            DOCUMENT_SIGNING_SCOPE,
            file!!,
            documentSigningParams
        )
    }

    fun getDocumentDownloadRequestModel(
        documentName: String?,
        documentURL: String?
    ): UAEPassDocumentDownloadRequestModel? {
        return UAEPassDocumentDownloadRequestModel(
            UAE_PASS_ENVIRONMENT,
            UAE_PASS_CLIENT_ID,
            UAE_PASS_CLIENT_SECRET,
            DOCUMENT_SIGNING_SCOPE,
            documentName!!,
            documentURL!!
        )
    }

    fun getProfileRequestModel(context: Context): UAEPassProfileRequestModel? {
        var ACR_VALUE = ""
        ACR_VALUE = if (isPackageInstalled(context.packageManager)) {
            ACR_VALUES_MOBILE
        } else {
            ACR_VALUES_WEB
        }
        return UAEPassProfileRequestModel(
            UAE_PASS_ENVIRONMENT,
            UAE_PASS_CLIENT_ID,
            UAE_PASS_CLIENT_SECRET,
            SCHEME,
            FAILURE_HOST,
            SUCCESS_HOST,
            REDIRECT_URL,
            MY_SCOPE,
            RESPONSE_TYPE,
            ACR_VALUE,
            MY_STATE
        )
    }

}