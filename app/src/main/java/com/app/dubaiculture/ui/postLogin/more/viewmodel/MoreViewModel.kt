package com.app.dubaiculture.ui.postLogin.more.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.app.dubaiculture.R
import com.app.dubaiculture.data.Result
import com.app.dubaiculture.data.repository.event.local.models.Events
import com.app.dubaiculture.data.repository.more.MoreModel
import com.app.dubaiculture.data.repository.more.MoreRepository
import com.app.dubaiculture.data.repository.more.local.PrivacyPolicy
import com.app.dubaiculture.data.repository.more.remote.request.PrivacyAndTermRequest
import com.app.dubaiculture.data.repository.profile.local.Favourite
import com.app.dubaiculture.infrastructure.ApplicationEntry
import com.app.dubaiculture.ui.base.BaseViewModel
import com.app.dubaiculture.utils.Constants
import com.app.dubaiculture.utils.event.Event
import kotlinx.coroutines.launch

class MoreViewModel @ViewModelInject constructor(application: Application,val moreRepository: MoreRepository) :
    BaseViewModel(application) {

    private val _termsCondition: MutableLiveData<Event<PrivacyPolicy>> = MutableLiveData()
    val termsCondition: LiveData<Event<PrivacyPolicy>> = _termsCondition

    private val _privacyPolice: MutableLiveData<Event<PrivacyPolicy>> = MutableLiveData()
    val privacyPolice: LiveData<Event<PrivacyPolicy>> = _privacyPolice


    fun termsCondition(locale :String){
        showLoader(true)
        viewModelScope.launch {
            when(val result =
                moreRepository.getTermsAndCondition(PrivacyAndTermRequest(locale))){
                is Result.Success->{
                    showLoader(false)
                    _termsCondition.value = result.value
                }
                is Result.Failure->{
                    showErrorDialog(message = Constants.Error.INTERNET_CONNECTION_ERROR)
                    showLoader(false)
                }
            }
        }
    }

    fun privacyPolicy(locale :String){
        showLoader(true)
        viewModelScope.launch {
            when(val result =
                moreRepository.getPrivacyPolicy(PrivacyAndTermRequest(locale))){
                is Result.Success->{
                    showLoader(false)
                    _privacyPolice.value = result.value

                }
                is Result.Failure->{
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
    fun servicesList(): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.museum_more,
                getApplication<Application>().getString(R.string.museum_services),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.public_more,
                getApplication<Application>().getString(R.string.public_libraries_services),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.renting_more,
                getApplication<Application>().getString(R.string.renting_more),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.general_more,
                getApplication<Application>().getString(R.string.general_more),
                R.drawable.forward_arrow,
                true
            )
        )
        list.add(
            MoreModel(
                R.drawable.heritage_more,
                getApplication<Application>().getString(R.string.heritage_more),
                R.drawable.forward_arrow,
                true
            )
        )
        return list
    }
    fun newsList(): ArrayList<MoreModel> {
        val list = ArrayList<MoreModel>()
        list.add(
            MoreModel(
                R.drawable.news_more,
                getApplication<Application>().getString(R.string.news_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.phone_more,
                getApplication<Application>().getString(R.string.contact_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.question_mark_more,
                getApplication<Application>().getString(R.string.help_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.mobile_more,
                getApplication<Application>().getString(R.string.related_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.noun_policy_more,
                getApplication<Application>().getString(R.string.privacy_more),
                R.drawable.forward_arrow,
                false
            )
        )
        list.add(
            MoreModel(
                R.drawable.privacy_more,
                getApplication<Application>().getString(R.string.terms_and_conditions),
                R.drawable.forward_arrow,
                false
            )
        )
        return list
    }
}