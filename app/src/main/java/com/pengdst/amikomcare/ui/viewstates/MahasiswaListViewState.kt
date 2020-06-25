package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.MahasiswaModel

data class MahasiswaListViewState (
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MutableList<MahasiswaModel>? = null,
        var error: Exception? = null
)