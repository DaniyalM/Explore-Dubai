package com.app.dubaiculture.ui.postLogin.explore.viewmodel

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.BuildConfig
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.attraction.local.models.Attractions
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.explore.ExploreRepository
import com.app.dubaiculture.data.repository.explore.local.models.AttractionsEvents
import com.app.dubaiculture.data.repository.explore.local.models.Explore
import com.app.dubaiculture.data.repository.explore.local.models.ExploreMap
import com.app.dubaiculture.data.repository.explore.remote.request.ExploreRequest
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.location.LocationHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class ExploreMapViewModel @ViewModelInject constructor(application: Application,val exploreRepository: ExploreRepository):BaseViewModel(application) {

    private val _exploreAttractionsEvents: MutableLiveData<Result<AttractionsEvents>> = MutableLiveData()
    val exploreAttractionsEvents: LiveData<Result<AttractionsEvents>> = _exploreAttractionsEvents


    fun getExploreMap(locale : String){
        showLoader(true)
        viewModelScope.launch {
            when(val result = exploreRepository.getExploreMap(ExploreRequest(culture = locale))){
                is Result.Success ->{
                    showLoader(false)
                    result.value.attractionCategory
                    result.value.events

                    _exploreAttractionsEvents.value = result
                }
                is Result.Failure ->{
                    showLoader(false)


                }
                is Result.Error ->{
                    showLoader(false)
                }
            }
        }
        showLoader(false)
    }


    fun eventFilter(locationHelper: LocationHelper,exploreMapList : ArrayList<ExploreMap> , eventList : List<Events> ,lat :Double?=null , lng : Double?=null):List<ExploreMap> {
        exploreMapList.clear()
        sortNearEvent(eventList,locationHelper,lat,lng).forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    image = it.image,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude!!,
                    lng = it.longitude!!,
                )
            )
        }
        return exploreMapList
    }



    fun sortNearEvent(list: List<Events>, locationHelper : LocationHelper, lat :Double?=null , lng : Double?=null): List<Events> {
        val myList = ArrayList<Events>()
        list.forEach {
            val distance = locationHelper.distance(lat?:24.8623,lng?: 67.0627,
                it.latitude.toString().ifEmpty { "24.83250180519734" }.toDouble(),
                it.longitude.toString().ifEmpty {"67.08119661055807"}.toDouble())
            it.distance = distance
            myList.sortWith(compareBy({ it.distance }))
            myList.add(it)
        }
        return myList
    }


    fun attractionFilter(category: String,locationHelper: LocationHelper,exploreMapList : ArrayList<ExploreMap>,attractions :ArrayList<Attractions>,lat :Double?=null , lng : Double?=null):List<ExploreMap>{
        exploreMapList.clear()
        val list =   sortNearAttraction(attractions,locationHelper,lat,lng).filter {
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
                        pinInRadius = it.withinRadiusIcon
                )
            )
        }

        return exploreMapList
    }

    fun sortNearAttraction(list: List<Attractions>,locationHelper : LocationHelper ,lat :Double?=null , lng : Double?=null): List<Attractions> {
        val myList = ArrayList<Attractions>()
        list.forEach {
            val distance = locationHelper.distance(lat?:24.8623,lng?: 67.0627,
                ((it.latitude.toString().ifEmpty { "24.83250180519734" }).toDouble()),
                (it.longitude.toString().ifEmpty {"67.08119661055807"}).toDouble())
            it.distance = distance
            myList.sortWith(compareBy({ it.distance }))
            myList.add(it)
        }
        return myList
    }





   fun mergeArrayList(exploreMapList : ArrayList<ExploreMap>,attractions :ArrayList<Attractions>,eventList: List<Events>,locationHelper : LocationHelper,lat :Double?=null , lng : Double?=null) : List<ExploreMap>{
        exploreMapList.clear()
        sortNearAttraction(attractions,locationHelper,lat,lng).forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    image = it.portraitImage,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude.toString().ifEmpty { "24.83250180519734" },
                    lng = it.longitude.toString().ifEmpty  {"67.08119661055807"},
                    pinInRadius = it.withinRadiusIcon,
                        pinOutRadius = it.outOfRadiusIcon
                )

            )
        }
        sortNearEvent(eventList,locationHelper,lat, lng).forEach {
            exploreMapList.add(
                ExploreMap(
                    id = it.id,
                    image = it.image,
                    title = it.title,
                    location = it.locationTitle,
                    distance = it.distance,
                    lat = it.latitude!!,
                    lng = it.longitude!!,
                )
            )
        }
        return exploreMapList
    }

    fun pinsOnMap(exploreMapList: List<ExploreMap>, map: GoogleMap){
        map.clear()
        exploreMapList.forEach {
            val locationObj = LatLng(it.lat.toString().ifEmpty { "24.83250180519734" }.toDouble(),
                it.lng.toString().ifEmpty { "67.08119661055807" }.toDouble())
            if(it.distance!! <= 6.0){
              val marker =  map.addMarker(MarkerOptions().position(locationObj)
                    .title(it.title))
                setURLIcon(it.pinInRadius.toString(),marker,getApplication<Application>())
            }
      else
            {
               val marker =  map.addMarker(MarkerOptions().position(locationObj)
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map_away))
                    .title(it.title))
                setURLIcon(it.pinOutRadius.toString(),marker,getApplication<Application>())

            }
        }
    }

    private fun setURLIcon(url :String ,marker : Marker,context : Context){
        Glide.with(context)
                .asBitmap()
                .load(BuildConfig.BASE_URL+url)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(bitmap: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                        if (bitmap != null) {
                            val icon = BitmapDescriptorFactory.fromBitmap(bitmap)
                            marker.setIcon(icon)
                        }
                    }
                })
    }
}