package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.PeriksaModel

data class PeriksaViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: PeriksaModel? = null,
        var error: Exception? = null
)