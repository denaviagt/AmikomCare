package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.ObatModel

data class ObatListState (
        var status: Int = RESULT_NO_STATE,
        var data: MutableList<ObatModel>? = null,
        var message: String? = null
)