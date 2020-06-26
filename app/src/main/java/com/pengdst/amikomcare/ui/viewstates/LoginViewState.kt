package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.BaseModel

data class LoginViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: BaseModel? = null,
        var message: String? = null,
        var error: Exception? = null
)