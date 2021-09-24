package com.app.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.clicklisteners

import com.app.dubaiculture.data.repository.sitemap.local.BeaconItems

interface BeaconClickListener {
    fun onClick(beaconItems: BeaconItems)
}