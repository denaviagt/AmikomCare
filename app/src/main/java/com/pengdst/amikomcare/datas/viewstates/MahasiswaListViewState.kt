package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.models.MahasiswaModel

data class MahasiswaListViewState (
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MutableList<MahasiswaModel>? = null,
        var error: Exception? = null
)