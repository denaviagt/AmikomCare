package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pengdst.amikomcare.datas.repositories.PasienRepository
import com.pengdst.amikomcare.ui.viewstates.PasienListViewState
import com.pengdst.amikomcare.ui.viewstates.PasienViewState

class PasienViewModel : ViewModel() {

    private val pasienRepository = PasienRepository()

    fun fetchPasien(idDokter: String, idMahasiswa: String) {
        pasienRepository.fetchPasien(idDokter, idMahasiswa)
    }

    fun fetchListPasien() {
        pasienRepository.fetchPasienList()
    }

    fun observePasienList(): LiveData<PasienListViewState> {
        return pasienRepository.livePasienList
    }

    fun observeSinglePasien(): LiveData<PasienViewState> {
        return pasienRepository.livePasien
    }
}