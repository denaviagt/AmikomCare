package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.PeriksaModel

data class PeriksaState(
        var status: Int = RESULT_NO_STATE,
        var data: PeriksaModel? = null,
        var message: String? = null
)