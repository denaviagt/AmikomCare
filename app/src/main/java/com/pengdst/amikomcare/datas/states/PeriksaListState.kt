package com.pengdst.amikomcare.datas.states

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.PeriksaModel

data class PeriksaListState(
        var status: Int = RESULT_NO_STATE,
        var data: MutableList<PeriksaModel>? = null,
        var message: String? = null
)