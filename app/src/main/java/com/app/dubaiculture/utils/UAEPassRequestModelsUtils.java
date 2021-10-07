package com.app.dubaiculture.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.app.dubaiculture.BuildConfig;

import java.io.File;
import java.util.Objects;

import ae.sdg.libraryuaepass.business.Environment;
import ae.sdg.libraryuaepass.business.authentication.model.UAEPassAccessTokenRequestModel;
import ae.sdg.libraryuaepass.business.documentsigning.model.DocumentSigningRequestParams;
import ae.sdg.libraryuaepass.business.documentsigning.model.UAEPassDocumentDownloadRequestModel;
import ae.sdg.libraryuaepass.business.documentsigning.model.UAEPassDocumentSigningRequestModel;
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestByAccessTokenModel;
import ae.sdg.libraryuaepass.business.profile.model.UAEPassProfileRequestModel;
import ae.sdg.libraryuaepass.utils.Utils;

import static ae.sdg.libraryuaepass.business.Environment.PRODUCTION;
import static ae.sdg.libraryuaepass.business.Environment.STAGING;

public class UAEPassRequestModelsUtils {

    //UAE PASS START -- ADD BELOW FIELDS
//    private static final String UAE_PASS_CLIENT_ID = <<"Enter Client Id Here">>;
//    private static final String UAE_PASS_CLIENT_SECRET = <<"Enter Clinet Secret Here">>;
//    private static final String REDIRECT_URL = <<"Enter Redirect URI Here">>;
//    private static final Environment UAE_PASS_ENVIRONMENT = <<Enter Environment here>>;
    private static final Environment UAE_PASS_ENVIRONMENT = Environment.STAGING;
    private static final String UAE_PASS_CLIENT_ID = "dcaa_app_stage";
    private static final String UAE_PASS_CLIENT_SECRET = "QyFfkEGfpwvSbSqc";
    private static final String REDIRECT_URL = "dc://com.dc.dc-int";
    //UAE PASS END -- ADD BELOW FIELDS

    private static final String DOCUMENT_SIGNING_SCOPE = "urn:safelayer:eidas:sign:process:document";
    private static final String RESPONSE_TYPE = "code";
    private static final String SCOPE = "urn:uae:digitalid:profile";
    private static final String ACR_VALUES_MOBILE = "urn:digitalid:authentication:flow:mobileondevice";
    private static final String ACR_VALUES_WEB = "urn:safelayer:tws:policies:authentication:level:low";
    private static final String UAE_PASS_PACKAGE_ID = "ae.uaepass.mainapp";
    private static final String UAE_PASS_QA_PACKAGE_ID = "ae.uaepass.mainapp.qa";

    private static final String SCHEME = BuildConfig.URI_SCHEME;
    private static final String FAILURE_HOST = BuildConfig.URI_HOST_FAILURE;
    private static final String SUCCESS_HOST = BuildConfig.URI_HOST_SUCCESS;
    private static final String STATE = Utils.INSTANCE.generateRandomString(24);
    private static final String MY_SCOPE = "urn:uae:digitalid:profile:general";
    private static final String MY_STATE = "HnlHOJTkTb66Y5H";
    private static boolean isPackageInstalled(PackageManager packageManager) {

        String packageName = null;
        if (UAEPassRequestModelsUtils.UAE_PASS_ENVIRONMENT == PRODUCTION) {
            packageName = UAE_PASS_PACKAGE_ID;
        } else if (UAEPassRequestModelsUtils.UAE_PASS_ENVIRONMENT == STAGING) {
            packageName = UAE_PASS_QA_PACKAGE_ID;
        }

        boolean found = true;
        try {
            packageManager.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            found = false;
        }

        return found;
    }

    public static UAEPassAccessTokenRequestModel getAuthenticationRequestModel(Context context) {
        String ACR_VALUE = "";
        if (isPackageInstalled(context.getPackageManager())) {
            ACR_VALUE = ACR_VALUES_MOBILE;
        } else {
            ACR_VALUE = ACR_VALUES_WEB;
        }
        return new UAEPassAccessTokenRequestModel(
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
        );
    }


    public static UAEPassDocumentSigningRequestModel getDocumentRequestModel(File file, DocumentSigningRequestParams documentSigningParams) {
        return new UAEPassDocumentSigningRequestModel(
                UAE_PASS_ENVIRONMENT,
                UAE_PASS_CLIENT_ID,
                UAE_PASS_CLIENT_SECRET,
                SCHEME,
                FAILURE_HOST,
                SUCCESS_HOST,
                Objects.requireNonNull(documentSigningParams.getFinishCallbackUrl()),
                DOCUMENT_SIGNING_SCOPE,
                file,
                documentSigningParams);
    }

    public static UAEPassDocumentDownloadRequestModel getDocumentDownloadRequestModel(String documentName, String documentURL) {
        return new UAEPassDocumentDownloadRequestModel(
                UAE_PASS_ENVIRONMENT,
                UAE_PASS_CLIENT_ID,
                UAE_PASS_CLIENT_SECRET,
                DOCUMENT_SIGNING_SCOPE,
                documentName,
                documentURL);

    }

    public static UAEPassProfileRequestModel getProfileRequestModel(Context context) {
        String ACR_VALUE = "";
        if (isPackageInstalled(context.getPackageManager())) {
            ACR_VALUE = ACR_VALUES_MOBILE;
        } else {
            ACR_VALUE = ACR_VALUES_WEB;
        }

        return new UAEPassProfileRequestModel(
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
                MY_STATE);
    }

    public static UAEPassProfileRequestByAccessTokenModel getUAEPassHavingAccessToken(String accessToken) {
        return new UAEPassProfileRequestByAccessTokenModel(
                UAE_PASS_ENVIRONMENT,  // done
                UAE_PASS_CLIENT_ID,
                accessToken,
                MY_STATE
        );
    }
}
