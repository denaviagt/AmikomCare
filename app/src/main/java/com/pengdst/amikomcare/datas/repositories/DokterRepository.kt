package com.pengdst.amikomcare.datas.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.ui.viewstates.DokterListViewState
import com.pengdst.amikomcare.ui.viewstates.DokterViewState

class DokterRepository : BaseFirebaseRepository() {

    val liveDokter = MutableLiveData<DokterViewState>().apply {
        value = DokterViewState()
    }
    val liveDokterList = MutableLiveData<DokterListViewState>().apply {
        value = DokterListViewState()
    }

    private val dbDokter = dbLogin.child(NODE_DOKTER)

    fun updateDokter(dokter: DokterModel){

        dbDokter.child(dokter.id!!).setValue(dokter)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        liveDokter.value = liveDokter.value?.copy(data = dokter, success = true)
                    }
                    else{
                        liveDokter.value = liveDokter.value?.copy(error = it.exception)
                    }
                }
    }

    fun fetchDokterList() {

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                @Suppress("UNUSED_VARIABLE") val
                        dokters = mutableListOf<DokterModel>()

                for (dokterSnapshots in snapshot.children) {

                    @Suppress("NAME_SHADOWING")
                    val dokters = mutableListOf<DokterModel>()

                    for (dokterSnapshot in snapshot.children) {

                        val dokter = dokterSnapshot.getValue(DokterModel::class.java)

                        dokter?.id = dokterSnapshot.key

                        dokter.let {
                            dokters.add(it!!)
                            liveDokterList.value?.data?.add(dokter!!)
                        }
                    }

                    liveDokterList.value = liveDokterList.value?.copy(data = dokters)
                }

            }

        })
    }

}