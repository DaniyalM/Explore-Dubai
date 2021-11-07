package com.dubaiculture.data.repository.login.local

import com.dubaiculture.data.repository.base.BaseLDS
import javax.inject.Inject

class UaeLDS
@Inject constructor(private val uaeDAO: UaeDAO) :
    BaseLDS<UAEPass>(uaeDAO) {

    suspend fun getUser(): UAEPass? {
        val list = uaeDAO.getAll()
        return if (list.isEmpty()) {
            null
        } else {
            list[0]
        }
    }
}