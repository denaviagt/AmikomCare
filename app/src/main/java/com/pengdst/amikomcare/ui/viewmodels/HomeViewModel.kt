package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.viewstates.AntrianListState
import com.pengdst.amikomcare.datas.viewstates.DokterState

class HomeViewModel : BaseMainViewModel() {

    fun fetchAntrianList(){
        antrianRepository.fetchAntrianList()
    }

    fun fetchAntrianList(idDokter: String){
        antrianRepository.fetchAntrianList(idDokter)
    }

    fun observeAntrianList(): LiveData<AntrianListState> {
        return antrianRepository.liveAntrianList
    }

    fun logout() {
        this.dokterRepository.liveDokter.value = DokterState()
    }

}
