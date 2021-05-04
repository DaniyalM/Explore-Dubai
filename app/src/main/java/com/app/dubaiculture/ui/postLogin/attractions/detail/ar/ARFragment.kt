package com.app.dubaiculture.ui.postLogin.attractions.detail.ar

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentARBinding
import com.app.dubaiculture.ui.base.BaseFragment
import com.app.dubaiculture.ui.postLogin.attractions.detail.ar.external.*
import com.app.dubaiculture.utils.Constants.AR.CLIENT_TOKEN
import com.app.dubaiculture.utils.Constants.AR.TARGET_ID
import com.app.dubaiculture.utils.Constants.NavBundles.META_DATA_ID
import com.app.dubaiculture.utils.ProgressDialog
import com.wikitude.NativeStartupConfiguration
import com.wikitude.WikitudeSDK
import com.wikitude.common.WikitudeError
import com.wikitude.common.camera.CameraSettings
import com.wikitude.common.rendering.RenderExtension
import com.wikitude.rendering.ExternalRendering
import com.wikitude.tracker.*
import kotlinx.android.synthetic.main.fragment_a_r.*
import org.json.JSONObject


class ARFragment : BaseFragment<FragmentARBinding>(), ExternalRendering,
    ImageTrackerListener {
    private var wikitudeSDK: WikitudeSDK? = null
    private var customSurfaceView: CustomSurfaceView? = null
    private var driver: Driver? = null
    private var glRenderer: GLRenderer? = null
    private var cloudRecognitionService: CloudRecognitionService? = null
    var viewGroup: ViewGroup? = null

//    override fun getTheme()=R.style.FullScreenDialog;
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialog)
//
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (dialog != null) {
//            val width = ViewGroup.LayoutParams.MATCH_PARENT
//            val height = ViewGroup.LayoutParams.MATCH_PARENT
//            dialog?.window!!.apply {
//                setLayout(width, height)
//                @Suppress("DEPRECATION")
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    insetsController?.hide(WindowInsets.Type.statusBars())
//                } else {
//                    setFlags(
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                        WindowManager.LayoutParams.FLAG_FULLSCREEN
//                    )
//                }
//
//            }
//
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wikitudeSDK = WikitudeSDK(this)
        val startupConfiguration = NativeStartupConfiguration()
        startupConfiguration.licenseKey = WikitudeSDKConstants.WIKITUDE_SDK_KEY
        startupConfiguration.cameraPosition = CameraSettings.CameraPosition.BACK
        wikitudeSDK!!.onCreate(requireContext(), requireContext(), startupConfiguration)
        cloudRecognitionService = wikitudeSDK!!.trackerManager.createCloudRecognitionService(
            CLIENT_TOKEN,
            TARGET_ID,
            object : CloudRecognitionServiceInitializationCallback {
                override fun onError(p0: WikitudeError?) {
                    Log.e("Error", "Error")
                }

                override fun onInitialized() {
                    Log.e("Success", " success")
                    wikitudeSDK!!.trackerManager.createImageTracker(
                        cloudRecognitionService,
                        this@ARFragment,
                        null
                    )

                }

            },
            null
        )
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

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
        customSurfaceView = CustomSurfaceView(requireContext(), glRenderer)
        driver = Driver(customSurfaceView, 30)

        val viewHolder = FrameLayout(requireContext())
        viewHolder.addView(customSurfaceView)
        val inflater = LayoutInflater.from(requireContext())
        val controls = inflater.inflate(R.layout.fragment_a_r, viewGroup, false)
        viewHolder.addView(controls)
        binding.llLayout.addView(viewHolder)
        btnRecognize.setOnClickListener {
            showLoader(true, customProgressDialog)
            cloudRecognitionService!!.recognize(object : CloudRecognitionServiceListener {
                override fun onResponse(response: CloudRecognitionServiceResponse?) {
                    if (response!!.isRecognized) {
                        Log.e("metadata=>", response.metadataString)
                        Log.e("response=>", response.toString())

                        val ja = JSONObject(response.metadataString)
                        val id = ja.getString("id")
                        Log.e("id", id.toString())

                        // This needs to be copied since access to the response is invalid after the end of the scope
                        val targetName = response.targetInformationsObject.name
                        requireActivity().runOnUiThread {
                            showLoader(false, customProgressDialog)

//                            Log.e("targetName",response.targetInformationsObject.name)
                            Toast.makeText(
                                activity,
                                "Recognition Success",
                                Toast.LENGTH_LONG
                            ).show()
                            val bundle = bundleOf(META_DATA_ID to id)
                            findNavController().navigate(
                                R.id.action_ARFragment_to_ARDetailFragment,
                                bundle
                            )
                        }
                    } else {
                        requireActivity().runOnUiThread {
                            showLoader(false, customProgressDialog)
//                            Log.e("targetName",response.targetInformationsObject.name)
                            Toast.makeText(
                                requireContext(),
                                "Recognition failed - Please try again",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onError(response: WikitudeError?) {
                    requireActivity().runOnUiThread {
                        showLoader(false, customProgressDialog)
                        Toast.makeText(
                            requireContext(),
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
        Log.e("onImageRecognized", target.name + target.uniqueId)

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
        Log.e("unique_id", target.name + target.uniqueId)
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
        p3: Int,
    ) {
    }

    fun showLoader(show: Boolean, customProgressDialog: ProgressDialog?) {
        if (show) {
            customProgressDialog?.apply {
                if (!isShowing) {
                    show()
                }
            }
        } else {
            customProgressDialog?.apply {
                if (isShowing) {
                    dismiss()
                }
                dismiss()

            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentARBinding.inflate(inflater, container, false)
}