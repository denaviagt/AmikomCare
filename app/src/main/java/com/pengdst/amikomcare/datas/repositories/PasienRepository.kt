package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.constants.RESULT_ERROR
import com.pengdst.amikomcare.datas.constants.RESULT_LOADING
import com.pengdst.amikomcare.datas.constants.RESULT_SUCCESS
import com.pengdst.amikomcare.datas.models.PasienModel

@Suppress("PrivatePropertyName")
class PasienRepository : BaseMainRepository() {
    private val TAG = "PasienRepository"

    fun fetchPasien(idDokter: String, idMahasiswa: String) {
        loading()
        dbPeriksa.child(idDokter).child(NODE_PASIEN).child(idMahasiswa)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG, "onCancelled() called with: error = $error")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val pasien = snapshot.getValue(PasienModel::class.java)

                        if (pasien != null) {
                            livePasien.value = livePasien.value?.copy(data = pasien, status = RESULT_SUCCESS)
                        }

                    }

                })
    }

    fun fetchPasien() {
        loadingList()
        dbPeriksa.child(NODE_PASIEN).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                livePasienList.value = livePasienList.value?.copy(status = RESULT_ERROR, message = error.message)
                Log.e(TAG, "onDataChange() called with: snapshot = ${error.details}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val periksas = mutableListOf<PasienModel>()

                for (dokterSnapshots in snapshot.children) {

                    val pasien = dokterSnapshots.getValue(PasienModel::class.java)

                    pasien?.let {
                        periksas.add(it)
                    }

                }

                livePasienList.value = livePasienList.value?.copy(data = periksas, status = RESULT_SUCCESS)
            }

        })
    }

    private fun loading() {
        livePasien.value = livePasien.value?.copy(status = RESULT_LOADING)
    }

    private fun loadingList() {
        livePasienList.value = livePasienList.value?.copy(status = RESULT_LOADING)
    }
}