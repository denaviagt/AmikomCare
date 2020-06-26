package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.PeriksaModel

data class PeriksaViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: PeriksaModel? = null,
        var error: Exception? = null
)