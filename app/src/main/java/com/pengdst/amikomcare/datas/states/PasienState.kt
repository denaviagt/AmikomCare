package com.pengdst.amikomcare.datas.states

import com.pengdst.amikomcare.datas.constants.RESULT_NO_STATE
import com.pengdst.amikomcare.datas.models.PasienModel

data class PasienState (
        var status: Int = RESULT_NO_STATE,
        var data: PasienModel? = null,
        var message: String? = null
)
