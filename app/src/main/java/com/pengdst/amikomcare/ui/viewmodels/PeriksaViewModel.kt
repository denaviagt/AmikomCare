package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pengdst.amikomcare.datas.models.*
import com.pengdst.amikomcare.datas.repositories.ObatRepository
import com.pengdst.amikomcare.datas.repositories.PeriksaRepository
import com.pengdst.amikomcare.ui.viewstates.ObatListViewState
import com.pengdst.amikomcare.ui.viewstates.ObatViewState
import com.pengdst.amikomcare.ui.viewstates.PeriksaListViewState
import com.pengdst.amikomcare.ui.viewstates.PeriksaViewState

// FIXME: 26/06/2020 Migrate to Repository
@Suppress("unused")
class PeriksaViewModel : ViewModel() {

    private val periksaRepository = PeriksaRepository()
    private val obatRepository = ObatRepository()

    fun fetchPeriksaList() {
        periksaRepository.fetchPeriksaList()
    }

    fun fetchPeriksa(dokter: DokterModel) {
        periksaRepository.fetchPeriksa(dokter)
    }

    fun fetchObat(idDokter: String, idMahasiswa: String, idObat: String) {
        obatRepository.fetchObat(idDokter, idMahasiswa, idObat)
    }

    fun fetchObatList(idDokter: String, idMahasiswa: String) {
        obatRepository.fetchObatList(idDokter, idMahasiswa)
    }

    fun addPeriksa(periksa: PeriksaModel) {
        periksaRepository.addPeriksa(periksa)
    }

    fun updatePeriksa(periksa: PeriksaModel) {
        periksaRepository.updatePeriksa(periksa)
    }

    fun observePeriksaList(): LiveData<PeriksaListViewState> {
        return periksaRepository.livePeriksaList
    }

    fun observePeriksa(): LiveData<PeriksaViewState> {
        return periksaRepository.livePeriksa
    }

    fun observeObat(): LiveData<ObatViewState> {
        return obatRepository.liveObat
    }

    fun observeObatList(): LiveData<ObatListViewState> {
        return obatRepository.liveObatList
    }

}