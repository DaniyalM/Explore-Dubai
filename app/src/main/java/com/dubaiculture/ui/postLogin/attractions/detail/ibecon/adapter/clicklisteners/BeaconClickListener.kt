package com.dubaiculture.ui.postLogin.attractions.detail.ibecon.adapter.clicklisteners

import com.dubaiculture.data.repository.sitemap.local.BeaconItems

interface BeaconClickListener {
    fun onClick(beaconItems: BeaconItems)
}