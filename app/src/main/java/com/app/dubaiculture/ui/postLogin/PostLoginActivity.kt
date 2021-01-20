package com.app.dubaiculture.ui.postLogin

//import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import android.os.Bundle
import androidx.activity.viewModels
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseAuthenticationActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.StreetViewPanoramaCamera
import com.google.android.gms.maps.model.StreetViewPanoramaLink
import com.google.android.gms.maps.model.StreetViewSource
import dagger.hilt.android.AndroidEntryPoint

//,
//OnStreetViewPanoramaReadyCallback
@AndroidEntryPoint
class PostLoginActivity : BaseAuthenticationActivity() {

    private val mainViewModel: MainViewModel by viewModels()
//    private lateinit var streetViewPanoramaFragment: SupportStreetViewPanoramaFragment

//    private fun showStreetView() {
//        streetViewPanoramaFragment =
//            supportFragmentManager
//                .findFragmentById(R.id.streetViewFragment) as SupportStreetViewPanoramaFragment
//        streetViewPanoramaFragment.let {
//            it.getStreetViewPanoramaAsync(this)
//        }
//
//    }

    override fun baseOnCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_post_login)
        subscribeUiEvents(mainViewModel)
//        showStreetView()

    }

//    override fun onStreetViewPanoramaReady(streetViewPanorama: StreetViewPanorama) {
////        val sanFrancisco = LatLng(37.754130, -122.447129)
////        streetViewPanorama.setPosition(sanFrancisco)
//        newView()
////        setLocationOfThePanorama(streetViewPanorama)
//
//        zoom(streetViewPanorama)
//        tilt(streetViewPanorama)
//        animate(streetViewPanorama)
//    }

//    private fun newView() {
//        val sanFrancisco = LatLng(37.754130, -122.447129)
//        val view = StreetViewPanoramaView(
//            this,
//            StreetViewPanoramaOptions().position(sanFrancisco)
//        )
//    }

//    private fun setLocationOfThePanorama(streetViewPanorama: StreetViewPanorama) {
//        val sanFrancisco = LatLng(37.754130, -122.447129)
//
//        // Set position with LatLng only.
//        streetViewPanorama.setPosition(sanFrancisco)
//
//        // Set position with LatLng and radius.
//        streetViewPanorama.setPosition(sanFrancisco, 20)
//
//        // Set position with LatLng and source.
//        streetViewPanorama.setPosition(sanFrancisco, StreetViewSource.OUTDOOR)
//
//        // Set position with LaLng, radius and source.
//        streetViewPanorama.setPosition(sanFrancisco, 20, StreetViewSource.OUTDOOR)
//
//        streetViewPanorama.location.links.firstOrNull()?.let { link: StreetViewPanoramaLink ->
//            streetViewPanorama.setPosition(link.panoId)
//        }
//    }

//    private fun zoom(streetViewPanorama: StreetViewPanorama) {
//        val zoomBy = 0.5f
//        val camera = StreetViewPanoramaCamera.Builder()
//            .zoom(streetViewPanorama.panoramaCamera.zoom + zoomBy)
//            .tilt(streetViewPanorama.panoramaCamera.tilt)
//            .bearing(streetViewPanorama.panoramaCamera.bearing)
//            .build()
//    }
//
//    private fun pan(streetViewPanorama: StreetViewPanorama) {
//        val panBy = 30f
//        val camera = StreetViewPanoramaCamera.Builder()
//            .zoom(streetViewPanorama.panoramaCamera.zoom)
//            .tilt(streetViewPanorama.panoramaCamera.tilt)
//            .bearing(streetViewPanorama.panoramaCamera.bearing - panBy)
//            .build()
//    }
//
//    private fun tilt(streetViewPanorama: StreetViewPanorama) {
//        var tilt = streetViewPanorama.panoramaCamera.tilt + 30
//        tilt = if (tilt > 90) 90f else tilt
//        val previous = streetViewPanorama.panoramaCamera
//        val camera = StreetViewPanoramaCamera.Builder(previous)
//            .tilt(tilt)
//            .build()
//    }
//
//    private fun animate(streetViewPanorama: StreetViewPanorama) {
//        // Keeping the zoom and tilt. Animate bearing by 60 degrees in 1000 milliseconds.
//        val duration: Long = 1000
//        val camera = StreetViewPanoramaCamera.Builder()
//            .zoom(streetViewPanorama.panoramaCamera.zoom)
//            .tilt(streetViewPanorama.panoramaCamera.tilt)
//            .bearing(streetViewPanorama.panoramaCamera.bearing - 60)
//            .build()
//        streetViewPanorama.animateTo(camera, duration)
//    }

}