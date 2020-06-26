package com.pengdst.amikomcare.ui.pages.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.states.AntrianListState
import com.pengdst.amikomcare.datas.states.DokterState

class HomeViewModel : BaseMainViewModel() {

    fun fetchAntrianList(){
        antrianRepository.fetchAntrianList()
    }

    fun observeAntrianList(): LiveData<AntrianListState> {
        return antrianRepository.liveAntrianList
    }

    fun logout() {
        dokterRepository.liveDokter.value = DokterState()
    }

}
