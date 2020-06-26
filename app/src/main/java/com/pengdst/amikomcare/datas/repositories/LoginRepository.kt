package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.ui.viewstates.LoginViewState

@Suppress("unused")
class LoginRepository : BaseFirebaseRepository() {
    @Suppress("PrivatePropertyName")
    private val TAG = "LoginRepository"

    // FIXME: 26/06/2020 Try use Datasource
    val loginViewState = MutableLiveData<LoginViewState>()

    private val dbDokter = dbLogin.child(NODE_DOKTER)

    fun observeUser(): MutableLiveData<LoginViewState> {
        return loginViewState
    }

    fun logout() {
        loginViewState.value = null
    }

    fun signIn(email: String, password: String) {

        Log.e(TAG, "signIn() called with: email = $email, password = $password")

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
}