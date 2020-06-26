package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.ObatModel

data class ObatListViewState (
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MutableList<ObatModel>? = null,
        var error: Exception? = null
)