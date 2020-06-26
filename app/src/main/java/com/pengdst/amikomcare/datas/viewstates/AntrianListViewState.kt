package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.AntrianModel

data class AntrianListViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MutableList<AntrianModel>? = null,
        var error: Exception? = null
)