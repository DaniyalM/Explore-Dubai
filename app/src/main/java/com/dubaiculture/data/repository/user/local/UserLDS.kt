package com.dubaiculture.data.repository.user.local

import com.dubaiculture.data.repository.base.BaseLDS
import com.dubaiculture.data.repository.user.local.guest.Guest
import javax.inject.Inject

class UserLDS @Inject constructor(private val userDao: UserDao) :
    BaseLDS<User>(userDao) {

    suspend fun getUser(): User? {
        val list = userDao.getAll()
        return if (list.isEmpty()) {
            null
        } else {
            list[0]
        }
    }
    }