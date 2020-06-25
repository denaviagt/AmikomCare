package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.DokterModel
import java.lang.Exception

data class LoginViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: DokterModel? = null,
        var message: String? = null,
        var error: Exception? = null
)