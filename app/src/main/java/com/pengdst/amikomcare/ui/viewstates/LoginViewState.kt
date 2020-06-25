package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.DokterModel

data class LoginViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: DokterModel? = null
)