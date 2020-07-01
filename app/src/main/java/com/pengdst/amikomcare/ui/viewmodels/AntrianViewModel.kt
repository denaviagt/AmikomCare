package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.states.AntrianListState

class AntrianViewModel : BaseMainViewModel() {

    val observePasienList: LiveData<AntrianListState> = antrianRepository.liveAntrianList

    fun fetchAntrianList() {
        antrianRepository.fetchAntrianList()
    }

}