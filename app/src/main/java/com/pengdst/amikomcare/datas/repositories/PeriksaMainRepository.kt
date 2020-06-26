package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.constants.RESULT_ERROR
import com.pengdst.amikomcare.datas.constants.RESULT_SUCCESS
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.datas.models.PeriksaModel

class PeriksaMainRepository : BaseMainRepository() {
    @Suppress("PrivatePropertyName")
    private val TAG = "PeriksaRepository"

    fun fetchPeriksaList() {

        dbPeriksa.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                livePeriksaList.value = livePeriksaList.value?.copy(status = RESULT_ERROR, message = error.message)
        }

            override fun onDataChange(snapshot: DataSnapshot) {

                val periksas = mutableListOf<PeriksaModel>()

                for (dokterSnapshots in snapshot.children) {

                    val periksa = dokterSnapshots.getValue(PeriksaModel::class.java)

                    periksa?.id = dokterSnapshots.key

                    periksa?.let {
                        periksas.add(it)
                    }

                }

                livePeriksaList.value = livePeriksaList.value?.copy(data = periksas, status = RESULT_SUCCESS)
            }

        })
    }

    fun fetchPeriksa(dokter: DokterModel) {

        dbPeriksa.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                livePeriksa.value = livePeriksa.value?.copy(message = error.message, status = RESULT_ERROR)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (pasienSnapshots in snapshot.children) {
                    val periksa = pasienSnapshots.getValue(PeriksaModel::class.java)
                            ?: PeriksaModel()

                    if (periksa.dokter?.id == dokter.id) {
                        periksa.id = pasienSnapshots.key

                        livePeriksa.value = livePeriksa.value?.copy(status = RESULT_SUCCESS, data = periksa)
                    }
                }

            }

        })
    }

    fun addPeriksa(periksa: PeriksaModel) {

        periksa.id = dbPeriksa.push().key
        dbPeriksa.child(periksa.id!!).setValue(periksa)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.e(TAG, "Result: ${it.result}")
                    } else
                        Log.e(TAG, "Error: ${it.exception}")
                }

    }

    fun updatePeriksa(periksa: PeriksaModel) {

        dbPeriksa.child(periksa.id!!).setValue(periksa)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "updatePeriksa() successfull ${it.result}")
                    } else{
                        Log.e(TAG, "updatePeriksa() error: ${it.exception}")
                    }
                }

    }

}