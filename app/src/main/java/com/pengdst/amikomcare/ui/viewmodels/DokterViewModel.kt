package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.datas.repositories.DokterRepository
import com.pengdst.amikomcare.datas.viewstates.DokterListViewState
import com.pengdst.amikomcare.datas.viewstates.DokterViewState

@Suppress("PrivatePropertyName", "unused")
class DokterViewModel : ViewModel(){

    private val TAG = "DokterViewModel"

    private val dokterRepository = DokterRepository()

    fun updateDokter(dokter: DokterModel){

        dokterRepository.updateDokter(dokter)

    }

    fun fetchDokterList() {
        dokterRepository.fetchDokterList()
    }

    fun observeDokterList(): LiveData<DokterListViewState> {
        return dokterRepository.liveDokterList
    }

    fun observeDokter(): LiveData<DokterViewState> {
        return dokterRepository.liveDokter
    }

}