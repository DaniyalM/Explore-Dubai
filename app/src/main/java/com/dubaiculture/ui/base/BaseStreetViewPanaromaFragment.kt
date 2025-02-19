package com.dubaiculture.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.dubaiculture.infrastructure.ApplicationEntry
import com.dubaiculture.utils.ProgressDialog
import com.dubaiculture.utils.event.EventUtilFunctions
import com.dubaiculture.utils.event.UiEvent
import com.google.android.gms.maps.StreetViewPanoramaFragment
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.squareup.otto.Bus
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

abstract class BaseStreetViewPanaromaFragment<DB : ViewDataBinding> : SupportStreetViewPanoramaFragment() {
    protected lateinit var application: ApplicationEntry
    protected var isBusRegistered: Boolean = false
    protected lateinit var bus: Bus
    protected lateinit var activity: Activity
    protected var customProgressDialog: ProgressDialog? = null
    protected lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>


    // data binding
    private lateinit var dataBinding: DB
    protected val binding get() = dataBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as Activity)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = activity.application as ApplicationEntry
        bus = application.bus
        bus.register(this)
        isBusRegistered = true
        customProgressDialog = ProgressDialog(activity)
        groupAdapter = GroupAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = getFragmentBinding(inflater, container)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //ViewModel is set as Binding Variable
        dataBinding.apply { lifecycleOwner = viewLifecycleOwner }
    }

    protected abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): DB

    override fun onDestroy() {
        super.onDestroy()
        if (isBusRegistered) {
            bus.unregister(this)
            isBusRegistered = false
        }
        groupAdapter.clear()

    }
    fun subscribeUiEvents(baseViewModel: BaseViewModel) {
        baseViewModel.uiEvents.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()
                ?.let { event ->
                    when (event) {
                        is UiEvent.ShowAlert -> {
//                            EventUtilFunctions.showAlert(event.message)
                        }
                        is UiEvent.ShowToast -> {
                            EventUtilFunctions.showToast(event.message, activity)
                        }
                        is UiEvent.ShowLoader -> {
                            EventUtilFunctions.showLoader(event.show, customProgressDialog)
                        }
                        is UiEvent.ShowSnackbar -> {
                            EventUtilFunctions.showSnackbar(
                                requireView(),
                                event.message,
                                event.action
                            )
                        }
                    }
                }
        })
    }

}