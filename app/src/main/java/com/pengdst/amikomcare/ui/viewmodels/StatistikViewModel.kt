package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.states.PasienListState

class StatistikViewModel: BaseMainViewModel() {

    fun fetchPasienList(idDokter: String) {
        pasienRepository.fetchPasienList(idDokter)
    }

    fun observePasienList(): LiveData<PasienListState> = pasienRepository.livePasienList

}