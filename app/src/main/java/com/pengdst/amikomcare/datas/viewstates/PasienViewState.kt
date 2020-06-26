package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.PasienModel

data class PasienViewState (
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: PasienModel? = null,
        var error: Exception? = null
)
