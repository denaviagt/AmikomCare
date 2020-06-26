package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pengdst.amikomcare.datas.repositories.LoginRepository
import com.pengdst.amikomcare.ui.viewstates.LoginViewState

class LoginViewModel : ViewModel() {

    private val loginRepository = LoginRepository()

    fun signIn(email: String, password: String) {
        loginRepository.signIn(email, password)
    }

    fun signIn(email: String) {
        loginRepository.signIn(email)
    }

    fun observeUser(): MutableLiveData<LoginViewState> {
        return loginRepository.liveLogin
    }

    fun logout() {
        loginRepository.logout()
    }

}