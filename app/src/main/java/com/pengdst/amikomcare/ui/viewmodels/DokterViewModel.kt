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

class DokterViewModel : ViewModel(){
    private val NODE_LOGIN = "login"
    private val NODE_DOKTER = "dokter"

    lateinit var session: LoginCallback

    val liveDataDokter = MutableLiveData<DokterModel>()
    val liveDataDokters = MutableLiveData<MutableList<DokterModel>>()

    private val db = FirebaseDatabase.getInstance().getReference(NODE_LOGIN)
    private val dbDokter = db.child(NODE_DOKTER)

    fun login(email: String, password: String) {
        dbDokter.addValueEventListener(object : ValueEventListener {
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

    fun fetchDokter() {
        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val dokters = mutableListOf<DokterModel>()

                for (dokterSnapshots in snapshot.children) {

                    val dokters = mutableListOf<DokterModel>()

                    for (dokterSnapshots in snapshot.children) {

                        val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                        dokter?.id = dokterSnapshots.key

                        dokter.let {
                            dokters.add(it!!)
                        }

                        session.onSuccess(dokter!!)

                        Log.d("LOGINVIEWMODEL", "Wrong Password or Email: $dokter")
                    }

                    liveDataDokters.value = dokters
                }

            }

        })
    }

    fun observeDokter(): LiveData<DokterModel> {
        return liveDataDokter
    }

    fun updateDokter(dokter: DokterModel){

        dbDokter.child(dokter.id!!).setValue(dokter)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        liveDataDokter.value = dokter
                    }
                    else{
                        liveDataDokter.value = null
                        Log.e("UpdateDokter", "updateDokter: Failed ${it.exception}")
                    }
                }
    }
}