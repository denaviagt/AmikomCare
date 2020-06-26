package com.pengdst.amikomcare.datas.repositories

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
    val liveLogin = MutableLiveData<LoginViewState>()

    private val dbDokter = dbLogin.child(NODE_DOKTER)

    fun logout() {
        liveLogin.value = null
    }

    fun signIn(email: String, password: String) {

        liveLogin.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveLogin.value = liveLogin.value?.copy(isSucces = false, error = error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var loginViewStateTemp = liveLogin.value?.copy()

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if ((dokter?.email?.equals(email) == true) && (dokter.password.equals(password))) {
                        dokter.id = dokterSnapshots.key

                        loginViewStateTemp = liveLogin.value?.copy(data = dokter, isSucces = true, message = "Sukses Login dengan Email: ${dokter.email}")
                    }
                }

                liveLogin.value = loginViewStateTemp?.copy(loading = false)
            }

        })
    }

    fun signIn(email: String) {

        liveLogin.value = LoginViewState(loading = true)

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveLogin.value = liveLogin.value?.copy(isSucces = false, error = error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var loginViewStateTemp = liveLogin.value?.copy()

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if (dokter?.email?.equals(email) == true) {
                        dokter.id = dokterSnapshots.key

                        loginViewStateTemp = liveLogin.value?.copy(data = dokter, isSucces = true, message = "Sukses Login dengan Email Google: ${dokter.email}")
                    }
                }

                liveLogin.value = loginViewStateTemp?.copy(loading = false)

            }

        })
    }
}