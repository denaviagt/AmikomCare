package com.pengdst.amikomcare.datas.states

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.MahasiswaModel

data class MahasiswaListState (
        var status: Int = RESULT_NO_STATE,
        var data: MutableList<MahasiswaModel>? = null,
        var message: String? = null
)