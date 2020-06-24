package com.pengdst.amikomcare.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.listeners.LoginCallback

class LoginViewModel : ViewModel() {

    companion object {
        @kotlin.jvm.JvmField
        var RC_SIGN_IN: Int = 1
        val TAG = "LoginViewModel"
    }

    var callback: LoginCallback? = null

    private val NODE_LOGIN = "login"
    private val NODE_DOKTER = "dokter"

    val liveDataDokter = MutableLiveData<DokterModel>()

    protected val db = FirebaseDatabase.getInstance().getReference(NODE_LOGIN)
    private val dbDokter = db.child(NODE_DOKTER)

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String) {
        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var found = false

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if ((dokter?.email?.equals(email) == true) && (dokter.password.equals(password))) {
                        dokter.id = dokterSnapshots.key

                        liveDataDokter.value = dokter
                        found = true
                    }
                }

                if (found) {
                    callback?.onSuccess(liveDataDokter.value!!)
                    Log.e(TAG, "Found User: ${liveDataDokter.value}")
                }
                else {
                    Log.e(TAG, "Wrong Password or Email: ${!found}")
                }
            }

        })
    }

    fun logout(){
        liveDataDokter.value = null
        auth.signOut()
    }

    fun signIn(email: String) {
        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var found = false

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if (dokter?.email?.equals(email) == true) {
                        dokter.id = dokterSnapshots.key

                        liveDataDokter.value = dokter
                        found = true
                    }
                }

                if (found) {
                    callback?.onSuccess(liveDataDokter.value!!)
                    Log.e(TAG, "Found User: ${liveDataDokter.value}")
                }
                else {
                    Log.e(TAG, "Wrong Password or Email: ${!found}")
                }
            }

        })
    }

}