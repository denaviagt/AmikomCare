package com.pengdst.amikomcare.datas.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.ui.viewstates.DokterListViewState
import com.pengdst.amikomcare.ui.viewstates.DokterViewState

class DokterRepository : BaseFirebaseRepository() {

    private val viewStateDokter = MutableLiveData<DokterViewState>().apply {
        value = DokterViewState()
    }
    private val viewStateListDokter = MutableLiveData<DokterListViewState>().apply {
        value = DokterListViewState()
    }

    private val dbDokter = dbLogin.child(NODE_DOKTER)

    fun updateDokter(dokter: DokterModel){

        dbDokter.child(dokter.id!!).setValue(dokter)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        viewStateDokter.value = viewStateDokter.value?.copy(data = dokter, success = true)
                    }
                    else{
                        viewStateDokter.value = viewStateDokter.value?.copy(error = it.exception)
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
                            viewStateListDokter.value?.data?.add(dokter!!)
                        }
                    }

                    viewStateListDokter.value = viewStateListDokter.value?.copy(data = dokters)
                }

            }

        })
    }

    fun observeDokter(): LiveData<DokterViewState> {
        return viewStateDokter
    }

    fun observeDokterList(): LiveData<DokterListViewState> {
        return viewStateListDokter
    }

}