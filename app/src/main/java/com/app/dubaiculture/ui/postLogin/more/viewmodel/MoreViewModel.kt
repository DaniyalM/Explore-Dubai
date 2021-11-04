package com.app.dubaiculture.ui.postLogin.more.viewmodel

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.more.MoreRepository
import com.app.dubaiculture.data.repository.more.local.ContactCenter
import com.app.dubaiculture.data.repository.more.local.ContactCenterLocation
import com.app.dubaiculture.data.repository.more.local.CultureConnoisseur
import com.app.dubaiculture.data.repository.more.local.PrivacyPolicy
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.Constants.PLAY_STORE.DUBAI_CULTURE
import com.app.dubaiculture.utils.Constants.PLAY_STORE.PACKAGE_NAME
import com.app.dubaiculture.utils.Constants.playStoreAppLink.OPEN_PLAYSTORE_APP
import com.app.dubaiculture.utils.Constants.playStoreAppLink.OPEN_PLAYSTORE_WEB
import com.app.dubaiculture.utils.event.Event
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MoreViewModel @Inject constructor(
    application: Application,
    val moreRepository: MoreRepository
) :
    BaseViewModel(application) {

    private val _cultureCon: MutableLiveData<Event<CultureConnoisseur>> = MutableLiveData()
    val cultureCon: LiveData<Event<CultureConnoisseur>> = _cultureCon

    private val _termsCondition: MutableLiveData<Event<PrivacyPolicy>> = MutableLiveData()
    val termsCondition: LiveData<Event<PrivacyPolicy>> = _termsCondition

    private val _privacyPolice: MutableLiveData<Event<PrivacyPolicy>> = MutableLiveData()
    val privacyPolice: LiveData<Event<PrivacyPolicy>> = _privacyPolice

    private val _contactUs: MutableLiveData<Event<ContactCenter>> = MutableLiveData()
    val contactUs: LiveData<Event<ContactCenter>> = _contactUs

    private val _notificationCount: MutableLiveData<Event<String>> = MutableLiveData()
    val notificationCount: LiveData<Event<String>> = _notificationCount

    val context = getApplication<ApplicationEntry>()


    fun getCultureConnoisseur(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                moreRepository.getCultureConnoisseur(
                    privacyAndTermRequest = PrivacyAndTermRequest(
                        culture = locale
                    )
                )) {
                is Result.Success -> {
                    showLoader(false)
                    _cultureCon.value = result.value
                }
                is Result.Failure -> {
                    showLoader(false)
                    showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)
                }
            }
        }
    }

    fun termsCondition(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                moreRepository.getTermsAndCondition(PrivacyAndTermRequest(locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _termsCondition.value = result.value
                }
                is Result.Failure -> {
                    showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)
                    showLoader(false)
                }
            }
        }
    }

    fun privacyPolicy(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result =
                moreRepository.getPrivacyPolicy(PrivacyAndTermRequest(locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _privacyPolice.value = result.value

                }
                is Result.Failure -> {
                    showLoader(false)

                }
            }
        }
    }

    fun contactUs(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = moreRepository.getContactCenter(PrivacyAndTermRequest(locale))) {
                is Result.Success -> {
                    showLoader(false)
                    _contactUs.value = result.value

                }
                is Result.Failure -> {
                    showLoader(false)

                }
            }
        }
    }

    fun notificationCount(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = moreRepository.getNotificationCount(locale)) {
                is Result.Success -> {
                    showLoader(false)
                    _notificationCount.value = Event(result.value)
                }
                is Result.Failure -> {
                    showLoader(false)

                }
            }
        }
    }

    fun setupToolbarWithSearchItems(
        logoImg: ImageView,
        pin: ImageView,
        tvTitle: TextView,
        heading: String
    ) {
        logoImg.visibility = View.GONE
        pin.visibility = View.GONE
        tvTitle.visibility = View.VISIBLE
        tvTitle.text = heading
    }


    fun setPinOnMap(map: GoogleMap, contactCenterLocation: ContactCenterLocation) {
        try {

            if (!contactCenterLocation.mapLatitude.isNullOrEmpty() && !contactCenterLocation.mapLongitude.isNullOrEmpty()) {
                val attractionLatLng = LatLng(
                    contactCenterLocation.mapLatitude.toDouble(),
                    contactCenterLocation.mapLongitude.toDouble()
                )


                map.addMarker(
                    MarkerOptions()
                        .position(attractionLatLng)
                        .title(
                            contactCenterLocation.subtitle
                        )
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))
                )
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        attractionLatLng, 14.0f
                    )
                )
                map.cameraPosition.target

            }
        } catch (e: NumberFormatException) {
            e.stackTrace
        }
    }

    fun playStoreOpen(context: Context) {
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(OPEN_PLAYSTORE_APP)))
        } catch (e: ActivityNotFoundException) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(OPEN_PLAYSTORE_WEB)))
        }
    }

    fun rateUs(context: Context) {
        val uri: Uri = Uri.parse("market://details?id=$PACKAGE_NAME")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            context.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$PACKAGE_NAME")
                )
            )
        }
    }

    fun shareAppLink(context: Context) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, DUBAI_CULTURE)
            var shareMessage = "\nLet me recommend you this application\n\n"
            shareMessage =
                """ ${shareMessage}https://play.google.com/store/apps/details?id=${PACKAGE_NAME}""".trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            Timber.e(e.stackTraceToString())
        }
    }


}