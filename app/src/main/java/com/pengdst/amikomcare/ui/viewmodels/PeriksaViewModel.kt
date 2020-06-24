package com.pengdst.amikomcare.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.datas.models.PeriksaModel

class PeriksaViewModel : BaseFirebaseViewModel() {

    val TAG = "PeriksaViewModel"

    val liveListPeriksa = MutableLiveData<MutableList<PeriksaModel>>()
    val livePeriksa = MutableLiveData<PeriksaModel>()

    fun fetch() {

        dbPeriksa.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val periksas = mutableListOf<PeriksaModel>()

                for (dokterSnapshots in snapshot.children) {

                    val periksa = dokterSnapshots.getValue(PeriksaModel::class.java)
                            ?: PeriksaModel()

                    periksa.id = dokterSnapshots.key

                    periksa.let {
                        periksas.add(it)
                    }

                }

                liveListPeriksa.value = periksas
            }

        })
    }

    fun fetch(dokter: DokterModel) {

        dbPeriksa.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (pasienSnapshots in snapshot.children) {
                    val periksa = pasienSnapshots.getValue(PeriksaModel::class.java) ?: PeriksaModel()

                    if (periksa.dokter?.id == dokter.id) {
                        periksa.id = pasienSnapshots.key

                        livePeriksa.value = periksa
                    }

                }

            }

        })
    }

    fun add(periksa: PeriksaModel) {

        periksa.id = dbPeriksa.push().key
        dbPeriksa.child(periksa.id!!).setValue(periksa)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.e(TAG, "Result: ${it.result}")
                    } else
                        Log.e(TAG, "Error: ${it.exception}")
                }

    }

    fun update(periksa: PeriksaModel) {

        dbPeriksa.child(periksa.id!!).setValue(periksa)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
//                        Log.e(TAG, "Result: ${it.isSuccessful}")
                    } else
                        Log.e(TAG, "Error: ${it.exception}")
                }

    }

    fun observeList(): LiveData<MutableList<PeriksaModel>> {
        return liveListPeriksa
    }

    fun observeSingle(): LiveData<PeriksaModel> {
        return livePeriksa
    }

}