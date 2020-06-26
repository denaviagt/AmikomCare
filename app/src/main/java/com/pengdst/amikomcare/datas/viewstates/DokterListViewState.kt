package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.DokterModel
import java.lang.Exception

data class DokterListViewState(
        var success: Boolean = false,
        var data: MutableList<DokterModel>? = null,
        var error: Exception? = null
)