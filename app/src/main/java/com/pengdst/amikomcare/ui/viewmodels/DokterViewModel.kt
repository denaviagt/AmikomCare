package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.ui.viewstates.DokterListViewState
import com.pengdst.amikomcare.ui.viewstates.DokterViewState

class DokterViewModel : BaseFirebaseViewModel(){

    private val TAG = "DokterViewModel"

    private val viewStateDokter = MutableLiveData<DokterViewState>().apply {
        value = DokterViewState()
    }
    private val viewStateAllAccount = MutableLiveData<DokterListViewState>().apply {
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

    fun fetchDokters() {

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val dokters = mutableListOf<DokterModel>()

                for (dokterSnapshots in snapshot.children) {

                    val dokters = mutableListOf<DokterModel>()

                    for (dokterSnapshot in snapshot.children) {

                        val dokter = dokterSnapshot.getValue(DokterModel::class.java)

                        dokter?.id = dokterSnapshot.key

                        dokter.let {
                            dokters.add(it!!)
                            viewStateAllAccount.value?.data?.add(dokter!!)
                        }
                    }

                    viewStateAllAccount.value = viewStateAllAccount.value?.copy(data = dokters)
                }

            }

        })
    }

    fun observeDokters(): LiveData<DokterListViewState> {
        return viewStateAllAccount
    }

    fun observeDokter(): LiveData<DokterViewState> {
        return viewStateDokter
    }

}