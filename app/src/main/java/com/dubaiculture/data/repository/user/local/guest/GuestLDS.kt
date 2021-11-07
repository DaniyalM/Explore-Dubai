package com.dubaiculture.data.repository.user.local.guest

import com.dubaiculture.data.repository.base.BaseLDS
import javax.inject.Inject

class GuestLDS @Inject constructor(private val guestDao: GuestDao) :BaseLDS<Guest>(guestDao){
        suspend fun getGuestUser(): Guest? {
        val list = guestDao.getGuestAll()
        return if (list.isEmpty()) {
            null
        } else {
            list[0]
        }
    }
}