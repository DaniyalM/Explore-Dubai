package com.app.dubaiculture.ui.postLogin.attractions.detail.ar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseActivity
import com.app.dubaiculture.ui.postLogin.attractions.detail.ar.external.*
import com.wikitude.NativeStartupConfiguration
import com.wikitude.WikitudeSDK
import com.wikitude.common.WikitudeError
import com.wikitude.common.camera.CameraSettings
import com.wikitude.common.rendering.RenderExtension
import com.wikitude.rendering.ExternalRendering
import com.wikitude.tracker.*

class ARActivity : BaseActivity(), ExternalRendering, ImageTrackerListener{
    private var wikitudeSDK: WikitudeSDK? = null
    private var customSurfaceView: CustomSurfaceView? = null
    private var driver: Driver? = null
    private var glRenderer: GLRenderer? = null
    private var cloudRecognitionService: CloudRecognitionService? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        wikitudeSDK = WikitudeSDK(this)
        val startupConfiguration = NativeStartupConfiguration()
        startupConfiguration.licenseKey = WikitudeSDKConstants.WIKITUDE_SDK_KEY
        startupConfiguration.cameraPosition = CameraSettings.CameraPosition.BACK
        wikitudeSDK!!.onCreate(this, this, startupConfiguration)

        cloudRecognitionService =   wikitudeSDK!!.trackerManager.createCloudRecognitionService(
            "74105670de0506eae3bf7d1c8e40c718",
            "606aaf5457b50a2f48283f9a",
            object : CloudRecognitionServiceInitializationCallback {
                override fun onError(p0: WikitudeError?) {
                    Log.e("Error","Error")
                }
                override fun onInitialized() {
                    Log.e("Success", " success")
//                    Toast.makeText(this@ARActivity, "Success", Toast.LENGTH_LONG).show()
//                    wikitudeSDK!!.trackerManager.createObjectTracker(cloudRecognitionService,this)
                    wikitudeSDK!!.trackerManager.createImageTracker(
                        cloudRecognitionService,
                        this@ARActivity,
                        null
                    )

                }

            },
            null
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }



    override fun onResume() {
        super.onResume()
        wikitudeSDK!!.onResume()
        customSurfaceView!!.onResume()
        driver!!.start()
    }

    override fun onPause() {
        super.onPause()
        customSurfaceView!!.onPause()
        driver!!.stop()
        wikitudeSDK!!.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        wikitudeSDK!!.onDestroy()
    }

    override fun onRenderExtensionCreated(renderExtension: RenderExtension?) {
        glRenderer = GLRenderer(renderExtension)
        wikitudeSDK!!.cameraManager.setRenderingCorrectedFovChangedListener(glRenderer)
        customSurfaceView = CustomSurfaceView(applicationContext, glRenderer)
        driver = Driver(customSurfaceView, 30)
        val viewHolder = FrameLayout(applicationContext)
        setContentView(viewHolder)
        viewHolder.addView(customSurfaceView)
        val inflater = LayoutInflater.from(applicationContext)
        val controls =
            inflater.inflate(R.layout.activity_a_r, null) as LinearLayout
        viewHolder.addView(controls)
        val recognizeButton = findViewById<Button>(R.id.on_click_cloud_tracking_recognize_button)
        recognizeButton.setOnClickListener {
            cloudRecognitionService!!.recognize(object : CloudRecognitionServiceListener {
                override fun onResponse(response: CloudRecognitionServiceResponse?) {
                    if (response!!.isRecognized) {
                        Log.e("metadata=>",response.metadataString)
                        Log.e("response=>",response.toString())
                        // This needs to be copied since access to the response is invalid after the end of the scope
                        val targetName = response.targetInformationsObject.name
                        runOnUiThread {
                            val targetInformationTextField =
                                findViewById<EditText>(R.id.on_click_cloud_tracking_info_field)
                            targetInformationTextField.setText(
                                targetName,
                                TextView.BufferType.NORMAL
                            )
                            targetInformationTextField.visibility = View.VISIBLE
                        }
                    } else {
                        runOnUiThread {
                            val targetInformationTextField =
                                findViewById<EditText>(R.id.on_click_cloud_tracking_info_field)
                            targetInformationTextField.setText(
                                "Recognition failed - Please try again",
                                TextView.BufferType.NORMAL
                            )
                            targetInformationTextField.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onError(response: WikitudeError?) {
                    runOnUiThread {
                        Toast.makeText(
                            this@ARActivity,
                            "Recognition failed - " + response!!.description,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })

        }

    }

    override fun onTargetsLoaded(p0: ImageTracker?) {
        Log.e("Success", "OnTargetLoaded.")
    }

    override fun onErrorLoadingTargets(p0: ImageTracker?, p1: WikitudeError?) {
        Log.e("Success", "onErrorLoadingTargets.")
    }

    override fun onImageRecognized(p0: ImageTracker?, target: ImageTarget) {
        Log.e("Success", "onImageRecognized.")
        Log.e("onImageRecognized",target.name + target.uniqueId)

        val strokedRectangle = StrokedRectangle(StrokedRectangle.Type.STANDARD)
        glRenderer!!.setRenderablesForKey(
            target.name + target.uniqueId,
            strokedRectangle,
            null
        )
    }

    override fun onImageTracked(p0: ImageTracker?, target: ImageTarget?) {
        Log.e("Success", "onImageTracked.")
        val strokedRectangle =
            glRenderer!!.getRenderableForKey(target!!.name + target.uniqueId) as StrokedRectangle
        Log.e("unique_id",target.name + target.uniqueId)
        if (strokedRectangle != null) {
            strokedRectangle.viewMatrix = target.viewMatrix
            strokedRectangle.xScale = target.targetScale.x
            strokedRectangle.yScale = target.targetScale.y
        }
    }

    override fun onImageLost(p0: ImageTracker?, target: ImageTarget) {
        Log.e("Success", "onImageLost.")

        glRenderer!!.removeRenderablesForKey(target.name + target.uniqueId)
    }

    override fun onExtendedTrackingQualityChanged(
        p0: ImageTracker?,
        p1: ImageTarget?,
        p2: Int,
        p3: Int
    ) {
    }
}