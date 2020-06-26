package com.pengdst.amikomcare.ui.viewstates

import com.pengdst.amikomcare.datas.models.MahasiswaModel

@Suppress("unused")
class MahasiswaViewState(
        var isSucces: Boolean = false,
        var loading: Boolean = false,
        var data: MahasiswaModel? = null,
        var error: Exception? = null
)