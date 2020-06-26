package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.AntrianModel

@Suppress("unused")
data class AntrianViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: AntrianModel? = null,
        var error: Exception? = null
)