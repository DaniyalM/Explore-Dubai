package com.app.dubaiculture.ui.postLogin.more.viewmodel

import android.app.Application
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.hilt.lifecycle.ViewModelInject
import com.app.dubaiculture.R
import com.app.dubaiculture.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.toolbar_layout.view.*

class MoreViewModel @ViewModelInject constructor(application: Application):BaseViewModel(application){



    fun setupToolbarWithSearchItems(logoImg : ImageView , pin :ImageView , tvTitle : TextView , heading : String) {
        logoImg.visibility = View.GONE
        pin.visibility = View.GONE
        tvTitle.visibility = View.VISIBLE
        tvTitle.text = heading
            }
        }