package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.ui.viewstates.LoginViewState

class LoginViewModel : BaseFirebaseViewModel() {

    companion object {
        @kotlin.jvm.JvmField
        var RC_SIGN_IN: Int = 1
        const val TAG = "LoginViewModel"
    }

    val loginViewState = MutableLiveData<LoginViewState>()

    private val dbDokter = dbLogin.child(NODE_DOKTER)

    fun logout() {
        loginViewState.value = null
    }

    fun signIn(email: String, password: String) {

        loginViewState.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                loginViewState.value = loginViewState.value?.copy(isSucces = false, error = error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var loginViewStateTemp = loginViewState.value?.copy()

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if ((dokter?.email?.equals(email) == true) && (dokter.password.equals(password))) {
                        dokter.id = dokterSnapshots.key

                        loginViewStateTemp = loginViewState.value?.copy(data = dokter, isSucces = true, message = "Sukses Login dengan Email: ${dokter.email}")
                    }
                }

                loginViewState.value = loginViewStateTemp?.copy(loading = false)
            }

        })
    }

    fun signIn(email: String) {

        loginViewState.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                loginViewState.value = loginViewState.value?.copy(isSucces = false, error = error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var loginViewStateTemp = loginViewState.value?.copy()

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if (dokter?.email?.equals(email) == true) {
                        dokter.id = dokterSnapshots.key

                        loginViewStateTemp = loginViewState.value?.copy(data = dokter, isSucces = true, message = "Sukses Login dengan Email Google: ${dokter.email}")
                    }
                }

                loginViewState.value = loginViewStateTemp?.copy(loading = false)

            }

        })
    }

    fun observeUser(): MutableLiveData<LoginViewState> {
        return loginViewState
    }

}