package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.DokterModel
import java.lang.Exception

data class DokterViewState (
        var success: Boolean = false,
        var data: DokterModel? = null,
        var error: Exception? = null
)