package com.pengdst.amikomcare.datas.repositories

import androidx.lifecycle.MutableLiveData
import com.pengdst.amikomcare.datas.viewstates.*

abstract class BaseMainRepository : BaseFirebaseRepository() {

    val livePeriksa = MutableLiveData<PeriksaState>().apply {
        value = PeriksaState()
    }
    val livePeriksaList = MutableLiveData<PeriksaListState>().apply {
        value = PeriksaListState()
    }

    val livePasien = MutableLiveData<PasienState>().apply {
        value = PasienState()
    }
    val livePasienList = MutableLiveData<PasienListState>().apply {
        value = PasienListState()
    }

    val liveObat = MutableLiveData<ObatState>().apply {
        value = ObatState()
    }
    val liveObatList = MutableLiveData<ObatListState>().apply {
        value = ObatListState()
    }

    val liveDokter = MutableLiveData<DokterState>().apply {
        value = DokterState()
    }

    val liveMahasiswaList = MutableLiveData<MahasiswaListState>().apply {
        value = MahasiswaListState()
    }

    val liveAntrianList = MutableLiveData<AntrianListState>().apply {
        value = AntrianListState()
    }
}