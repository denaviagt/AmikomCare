package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.constants.RESULT_ERROR
import com.pengdst.amikomcare.datas.constants.RESULT_SUCCESS
import com.pengdst.amikomcare.datas.models.ObatModel

class ObatMainRepository : BaseMainRepository() {
    @Suppress("PrivatePropertyName")
    private val TAG = "ObatRepository"

    fun fetchObat(idDokter: String, idMahasiswa: String, idObat: String) {

        liveObat.value = liveObat.value?.copy()

        dbPeriksa.child(idDokter).child(NODE_PASIEN).child(idMahasiswa).child(NODE_OBAT).child(idObat)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG, "onCancelled() called with: error = $error")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val pasien = snapshot.getValue(ObatModel::class.java)

                        if (pasien != null) {
                            liveObat.value = liveObat.value?.copy(data = pasien, status = RESULT_SUCCESS)
                        }
                    }

                })

    }

    fun fetchObatList(idDokter: String, idMahasiswa: String) {

        liveObatList.value = liveObatList.value?.copy()

        dbPeriksa.child(idDokter).child(NODE_PASIEN).child(idMahasiswa).child(NODE_DIAGNOSA).child(NODE_OBAT)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG, "onCancelled() called with: error ", error.toException())
                        liveObatList.value = liveObatList.value?.copy(status = RESULT_ERROR, message = error.message)
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        val obats = mutableListOf<ObatModel>()

                        snapshot.children.forEach { obatSnapshot ->

                            val obat = obatSnapshot.getValue(ObatModel::class.java)

                            obat?.id = obatSnapshot.key

                            obat?.let {
                                obats.add(it)
                            }

                        }

                        liveObatList.value = liveObatList.value?.copy(data = obats, status = RESULT_SUCCESS)

                    }

                })

    }
}