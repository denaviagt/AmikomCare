package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.ObatModel
import com.pengdst.amikomcare.datas.viewstates.ObatListViewState
import com.pengdst.amikomcare.datas.viewstates.ObatViewState

class ObatRepository : BaseFirebaseRepository() {
    @Suppress("PrivatePropertyName")
    private val TAG = "ObatRepository"

    val liveObatList = MutableLiveData<ObatListViewState>().apply {
        value = ObatListViewState()
    }
    val liveObat = MutableLiveData<ObatViewState>().apply {
        value = ObatViewState()
    }

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
                            liveObat.value = liveObat.value?.copy(data = pasien, isSucces = true, loading = false)
                        }

                        liveObat.value = liveObat.value?.copy(loading = false)
                    }

                })

    }

    fun fetchObatList(idDokter: String, idMahasiswa: String) {

        liveObatList.value = liveObatList.value?.copy()

        dbPeriksa.child(idDokter).child(NODE_PASIEN).child(idMahasiswa).child(NODE_DIAGNOSA).child(NODE_OBAT)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(TAG, "onCancelled() called with: error ", error.toException())
                        liveObatList.value = liveObatList.value?.copy(isSucces = false, error = error.toException())
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

                        liveObatList.value = liveObatList.value?.copy(data = obats, isSucces = true, loading = false)

                    }

                })

    }
}