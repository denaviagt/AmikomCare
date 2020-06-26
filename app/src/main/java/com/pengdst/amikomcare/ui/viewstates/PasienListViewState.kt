package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.PasienModel

data class PasienListViewState (
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MutableList<PasienModel>? = null,
        var error: Exception? = null
)
