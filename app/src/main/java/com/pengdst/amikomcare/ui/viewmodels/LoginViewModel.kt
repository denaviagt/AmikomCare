package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.viewstates.DokterState

class LoginViewModel : BaseMainViewModel() {

    fun signIn(email: String, password: String) {
        dokterRepository.fetchDokter(email, password)
    }

    fun signIn(email: String) {
        dokterRepository.fetchDokter(email)
    }

    fun observeUser(): LiveData<DokterState> {
        return dokterRepository.liveDokter
    }

}