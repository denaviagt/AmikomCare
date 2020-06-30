package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.datas.models.PeriksaModel
import com.pengdst.amikomcare.datas.states.AntrianListState
import com.pengdst.amikomcare.datas.states.PeriksaState

class PeriksaViewModel : BaseMainViewModel() {

    fun updatePeriksa(periksa: PeriksaModel) {
        periksaRepository.updatePeriksa(periksa)
    }
    fun fetchPeriksa(dokter: DokterModel) {
        periksaRepository.fetchPeriksa(dokter)
    }
    fun fetchPeriksaList() {
        periksaRepository.fetchPeriksaList()
    }

    fun fetchObatList(idDokter: String, idMahasiswa: String) {
        obatRepository.fetchObatList(idDokter, idMahasiswa)
    }

    fun fetchPasien(idDokter: String, idMahasiswa: String) {
        pasienRepository.fetchPasien(idDokter, idMahasiswa)
    }
    fun fetchListPasien() {
        pasienRepository.fetchPasienList()
    }

    fun observePeriksa(): LiveData<PeriksaState> {
        return periksaRepository.livePeriksa
    }

    fun deleteAntrian(id: String?){
        antrianRepository.removeAntrian(id)
    }

    fun observeAntrianList(): LiveData<AntrianListState> = antrianRepository.liveAntrianList

}