package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.PasienModel
import com.pengdst.amikomcare.datas.models.PeriksaModel
import com.pengdst.amikomcare.datas.viewstates.PasienListViewState
import com.pengdst.amikomcare.datas.viewstates.PasienViewState

@Suppress("PrivatePropertyName")
class PasienRepository : BaseFirebaseRepository() {
    private val TAG = "PasienRepository"

    val livePasien = MutableLiveData<PasienViewState>()
    val livePasienList = MutableLiveData<PasienListViewState>()

    fun fetchPasien(idDokter: String, idMahasiswa: String) {

        livePasien.value = PasienViewState(loading = true)

        dbPeriksa.child(idDokter).child(NODE_PASIEN).child(idMahasiswa)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG, "onCancelled() called with: error = $error")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val pasien = snapshot.getValue(PasienModel::class.java)

                        if (pasien != null) {
                            livePasien.value = livePasien.value?.copy(data = pasien, isSucces = true, loading = false)
                        }
                        livePasien.value = livePasien.value?.copy(loading = false)
                    }

                })
    }

    fun fetchPasienList() {

        livePasienList.value = PasienListViewState(loading = true)

        dbPeriksa.child(NODE_PASIEN).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                livePasienList.value = livePasienList.value?.copy(loading = false, error = error.toException())
                Log.e(TAG, "onDataChange() called with: snapshot = ${error.details}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val periksas = mutableListOf<PasienModel>()

                for (dokterSnapshots in snapshot.children) {

                    val pasien = dokterSnapshots.getValue(PasienModel::class.java)

                    pasien?.let {
                        periksas.add(it)
                    }

                    Log.e(TAG, "onDataChange() called with: snapshot = $pasien")

                }

                livePasienList.value = livePasienList.value?.copy(data = periksas, loading = false, isSucces = true)
            }

        })
    }
}