package com.pengdst.amikomcare.datas.states

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.ObatModel

data class ObatState (
        var status: Int = RESULT_NO_STATE,
        var data: ObatModel? = null,
        var message: String? = null
)