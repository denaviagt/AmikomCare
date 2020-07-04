package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.DokterModel

data class DokterListState(
        var status: Int = RESULT_NO_STATE,
        var data: MutableList<DokterModel>? = null,
        var message: String? = null
)