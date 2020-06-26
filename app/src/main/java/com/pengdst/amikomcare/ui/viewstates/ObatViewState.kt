package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.ObatModel

data class ObatViewState (
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: ObatModel? = null,
        var error: Exception? = null
)