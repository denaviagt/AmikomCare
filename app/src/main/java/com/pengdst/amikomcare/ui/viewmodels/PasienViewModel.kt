package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.states.PasienListState

class PasienViewModel : BaseMainViewModel(){

    fun fetchPasienList(idDokter: String){
        pasienRepository.fetchPasienList(idDokter)
    }

    val observePasienList: LiveData<PasienListState> = pasienRepository.livePasienList
}