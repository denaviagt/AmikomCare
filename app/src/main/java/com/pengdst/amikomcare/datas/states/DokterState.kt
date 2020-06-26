package com.pengdst.amikomcare.datas.states

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.DokterModel

data class DokterState (
        var status: Int = RESULT_NO_STATE,
        var data: DokterModel? = null,
        var message: String? = null
)