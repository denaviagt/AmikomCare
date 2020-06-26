package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.ObatModel

data class ObatListViewState (
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MutableList<ObatModel>? = null,
        var error: Exception? = null
)