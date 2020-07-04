package com.pengdst.amikomcare.datas.viewstates

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.PasienModel

data class PasienListState (
        var status: Int = RESULT_NO_STATE,
        var data: MutableList<PasienModel>? = null,
        var message: String? = null
)
