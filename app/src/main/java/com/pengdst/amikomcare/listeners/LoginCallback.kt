package com.pengdst.amikomcare.listeners

import com.pengdst.amikomcare.datas.models.DokterModel

interface LoginCallback {
    fun onSuccess(dokter: DokterModel)
}