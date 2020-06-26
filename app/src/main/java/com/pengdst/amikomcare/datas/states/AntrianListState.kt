package com.pengdst.amikomcare.datas.states

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.AntrianModel

data class AntrianListState(
        var status: Int = RESULT_NO_STATE,
        var data: MutableList<AntrianModel>? = null,
        var message: String? = null
)