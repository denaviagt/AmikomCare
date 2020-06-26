package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.PeriksaModel

data class PeriksaListViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MutableList<PeriksaModel>? = null,
        var error: Exception? = null
)