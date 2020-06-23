package com.pengdst.amikomcare.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.listeners.LoginCallback

class LoginViewModel : ViewModel() {
    private val NODE_LOGIN = "login"
    private val NODE_DOKTER = "dokter"

    lateinit var session: LoginCallback

    val liveDataDokter = MutableLiveData<DokterModel>()

    val liveToken = MutableLiveData<String>()

    private val db = FirebaseDatabase.getInstance().getReference(NODE_LOGIN)

    fun fetchDokter(email: String, password: String) {
        db.child(NODE_DOKTER).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val dokters = mutableListOf<DokterModel>()

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if ((dokter?.email?.equals(email) == true) && (dokter.password.equals(password))) {
                        dokter.id = dokterSnapshots.key
                        dokter.let {
                            dokters.add(it)
                        }

                        liveDataDokter.value = dokter

                        session.onSuccess(dokter)
                    }
                    else Log.d("LOGINVIEWMODEL", "Wrong Password or Email: $dokter")
                }

            }

        })
    }

    fun observeDokter(): LiveData<DokterModel> {
        return liveDataDokter
    }
}