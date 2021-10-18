package com.app.dubaiculture.data.repository.login.local

import com.app.dubaiculture.data.repository.base.BaseLDS
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