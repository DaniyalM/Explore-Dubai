package com.dubaiculture.data.repository.profile.local

import com.dubaiculture.data.repository.base.BaseLDS
import com.dubaiculture.data.repository.user.local.User
import com.dubaiculture.data.repository.user.local.UserDao
import javax.inject.Inject

class ProfileLDS @Inject constructor(private val profileDao: ProfileDao) :
    BaseLDS<Profile>(profileDao) {

    suspend fun getProfile(): Profile? {
        val list = profileDao.getProfile()
        return if (list.isEmpty()) {
            null
        } else {
            list[0]
        }
    }
}