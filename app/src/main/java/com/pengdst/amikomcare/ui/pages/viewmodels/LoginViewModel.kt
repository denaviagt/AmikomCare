package com.pengdst.amikomcare.ui.pages.viewmodels

import androidx.lifecycle.LiveData
import com.pengdst.amikomcare.datas.states.DokterState

class LoginViewModel : BaseMainViewModel() {

    fun signIn(email: String, password: String) {
        liveDokter.fetchDokter(email, password)
    }

    fun signIn(email: String) {
        liveDokter.fetchDokter(email)
    }

    fun observeUser(): LiveData<DokterState> {
        return liveDokter.liveDokter
    }

}