package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.pengdst.amikomcare.datas.repositories.*

abstract class BaseMainViewModel : ViewModel() {

    protected val antrianRepository = AntrianRepository()
    protected val dokterRepository = DokterRepository()
    protected val liveDokter = DokterRepository()
    protected val periksaRepository = PeriksaRepository()
    protected val obatRepository = ObatRepository()
    protected val pasienRepository = PasienRepository()

}