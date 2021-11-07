package com.dubaiculture.ui.postLogin.explore.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dubaiculture.BuildConfig
import com.dubaiculture.data.Result
import com.dubaiculture.data.repository.attraction.local.models.Attractions
import com.dubaiculture.data.repository.event.local.models.Events
import com.dubaiculture.data.repository.explore.ExploreRepository
import com.dubaiculture.data.repository.explore.local.models.AttractionsEvents
import com.dubaiculture.data.repository.explore.local.models.ExploreMap
import com.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.ui.base.BaseViewModel
import com.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreMapViewModel @Inject constructor(
    application: Application,
    val exploreRepository: ExploreRepository
) : BaseViewModel(application) {

    private val _exploreAttractionsEvents: MutableLiveData<Result<AttractionsEvents>> =
        MutableLiveData()
    val exploreAttractionsEvents: LiveData<Result<AttractionsEvents>> = _exploreAttractionsEvents
    private val context = getApplication<ApplicationEntry>()

    init {
        getExploreMap(context.auth.locale.toString())

    }

    fun getExploreMap(locale: String) {
        showLoader(true)
        viewModelScope.launch {
            when (val result = exploreRepository.getExploreMap(ExploreRequest(culture = locale))) {
                is Result.Success -> {
                    showLoader(false)
                    result.value.attractionCategory
                    result.value.events

                    _exploreAttractionsEvents.value = result
                }
                is Result.Failure -> {
                    showLoader(false)


                }
                is Result.Error -> {
                    showLoader(false)
                }
            }
        }
        showLoader(false)
    }


    fun eventFilter(
        locationHelper: LocationHelper,
        exploreMapList: ArrayList<ExploreMap>,
        eventList: List<Events>,
        lat: Double? = null,
        lng: Double? = null
    ): List<ExploreMap> {
        exploreMapList.clear()
        sortNearEvent(eventList, locationHelper, lat, lng).forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    image = it.image,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude,
                    lng = it.longitude,
                    isAttraction = false
                )
            )
        }
        return exploreMapList
    }


    fun sortNearEvent(
        list: List<Events>,
        locationHelper: LocationHelper,
        lat: Double? = null,
        lng: Double? = null
    ): List<Events> {
        val myList = ArrayList<Events>()
        list.forEach {
            val distance = locationHelper.distance(
                lat ?: 24.8623, lng ?: 67.0627,
                it.latitude.toString().ifEmpty { "24.83250180519734" }.toDouble(),
                it.longitude.toString().ifEmpty { "67.08119661055807" }.toDouble()
            )
            it.distance = distance
            myList.sortWith(compareBy({ it.distance }))
            myList.add(it)
        }
        return myList
    }

    fun attractionFilter(
        category: String,
        locationHelper: LocationHelper,
        exploreMapList: ArrayList<ExploreMap>,
        attractions: ArrayList<Attractions>,
        lat: Double? = null,
        lng: Double? = null
    ): List<ExploreMap> {
        exploreMapList.clear()
        val list = sortNearAttraction(attractions, locationHelper, lat, lng).filter {
            it.category.trim() == category
        }
        list.forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    image = it.portraitImage,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude!!,
                    lng = it.longitude!!,
                    pinOutRadius = it.outOfRadiusIcon,
                    pinInRadius = it.withinRadiusIcon,
                    isAttraction = true
                )
            )
        }

        return exploreMapList
    }

    fun sortNearAttraction(
        list: List<Attractions>,
        locationHelper: LocationHelper,
        lat: Double? = null,
        lng: Double? = null
    ): List<Attractions> {
        val myList = ArrayList<Attractions>()
        list.forEach {
            val distance = locationHelper.distance(
                lat ?: 24.8623, lng ?: 67.0627,
                ((it.latitude.toString().ifEmpty { "24.83250180519734" }).toDouble()),
                (it.longitude.toString().ifEmpty { "67.08119661055807" }).toDouble()
            )
            it.distance = distance
            myList.sortWith(compareBy({ it.distance }))
            myList.add(it)
        }
        return myList
    }


    fun mergeArrayList(
        exploreMapList: ArrayList<ExploreMap>,
        attractions: ArrayList<Attractions>,
        eventList: List<Events>,
        locationHelper: LocationHelper,
        lat: Double? = null,
        lng: Double? = null
    ): List<ExploreMap> {
        exploreMapList.clear()

        sortNearEvent(eventList, locationHelper, lat, lng).forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    image = it.image,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude,
                    lng = it.longitude,
                    isAttraction = false
                )
            )
        }
        sortNearAttraction(attractions, locationHelper, lat, lng).forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    image = it.portraitImage,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude.toString().ifEmpty { "24.83250180519734" },
                    lng = it.longitude.toString().ifEmpty { "67.08119661055807" },
                    pinInRadius = it.withinRadiusIcon,
                    pinOutRadius = it.outOfRadiusIcon,
                    isAttraction = true
                )

            )
        }
        return exploreMapList
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return if (vectorResId != 0) {
            ContextCompat.getDrawable(context, vectorResId)?.run {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                val bitmap =
                    Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
                draw(Canvas(bitmap))
                BitmapDescriptorFactory.fromBitmap(bitmap)
            }
        } else null

    }


    fun pinsOnMap(
        exploreMapList: List<ExploreMap>,
        map: GoogleMap,
        inRangeIcon: Int = 0,
        outRangeIcon: Int = 0,
        inRangeEventIcon: Int = 0,
        outRangeEventIcon: Int = 0
    ) {
        map.clear()
        exploreMapList.forEach {

            val locationObj = LatLng(
                it.lat.toString().ifEmpty { "24.83250180519734" }.toDouble(),
                it.lng.toString().ifEmpty { "67.08119661055807" }.toDouble()
            )
            if (it.distance!! <= 6.0) {
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(locationObj)
                        .title(it.title)
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.events_map))


                )
                if (it.isAttraction) {
                    bitmapDescriptorFromVector(context, inRangeIcon)?.apply {
                        marker?.setIcon(this)
                    }

                } else {
                    bitmapDescriptorFromVector(context, inRangeEventIcon)?.apply {
                        marker?.setIcon(this)
                    }
                }
//                setURLIcon(it.pinInRadius.toString(), marker, getApplication<Application>())
            } else {
                val marker = map.addMarker(
                    MarkerOptions().position(locationObj)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map_away))
                        .title(it.title)

                )
                if (it.isAttraction) {
                    bitmapDescriptorFromVector(context, outRangeIcon)?.apply {
                        marker?.setIcon(this)
                    }
                } else {
                    bitmapDescriptorFromVector(context, outRangeEventIcon)?.apply {
                        marker?.setIcon(this)
                    }
                }
//                setURLIcon(it.pinOutRadius.toString(), marker, getApplication<Application>())

            }
        }
    }

    private fun setURLIcon(url: String, marker: Marker, context: Context) {


        Glide.with(context)
            .asBitmap()
            .load(BuildConfig.BASE_URL + url)
            .dontTransform()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    bitmap: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    if (bitmap != null) {
//                        val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
                        val scale: Float = context.resources.displayMetrics.density
                        val pixels = (50 * scale + 0.5f).toInt()
                        val bitmap1 = Bitmap.createScaledBitmap(bitmap, pixels, pixels, true)
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap1))
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })
    }
}