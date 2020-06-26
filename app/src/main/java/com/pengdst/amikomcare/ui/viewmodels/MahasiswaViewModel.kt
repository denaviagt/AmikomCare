package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pengdst.amikomcare.datas.repositories.AntrianRepository
import com.pengdst.amikomcare.datas.repositories.MahasiswaRepository
import com.pengdst.amikomcare.ui.viewstates.AntrianListViewState

@Suppress("unused")
class MahasiswaViewModel : ViewModel() {
    @Suppress("PropertyName")
    val TAG = "MahasiswaViewModel"

    private val mahasiswaRepository = MahasiswaRepository()
    private val antrianRepository = AntrianRepository()

    fun fetchMahasiswaList(){
        mahasiswaRepository.fetchMahasiswaList()
    }

    fun fetchAntrianList(){
        antrianRepository.fetchAntrianList()
    }

    fun observeAntrianList(): LiveData<AntrianListViewState> {
        return antrianRepository.liveAntrianList
    }
}
