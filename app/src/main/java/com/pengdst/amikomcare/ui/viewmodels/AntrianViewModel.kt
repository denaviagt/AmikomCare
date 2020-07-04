package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.viewstates.AntrianListState

class AntrianViewModel : BaseMainViewModel() {

    val observePasienList: LiveData<AntrianListState> = antrianRepository.liveAntrianList

    fun fetchAntrianList() {
        antrianRepository.fetchAntrianList()
    }

}