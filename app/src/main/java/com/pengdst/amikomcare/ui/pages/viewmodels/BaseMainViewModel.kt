package com.pengdst.amikomcare.ui.pages.viewmodels

import androidx.lifecycle.ViewModel
import com.pengdst.amikomcare.datas.repositories.*

abstract class BaseMainViewModel : ViewModel() {

    protected val antrianRepository = AntrianMainRepository()
    protected val dokterRepository = DokterMainRepository()
    protected val liveDokter = DokterMainRepository()
    protected val periksaRepository = PeriksaMainRepository()
    protected val obatRepository = ObatMainRepository()
    protected val pasienRepository = PasienMainRepository()

}