package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.datas.models.PeriksaModel
import com.pengdst.amikomcare.ui.viewstates.PeriksaListViewState
import com.pengdst.amikomcare.ui.viewstates.PeriksaViewState

class PeriksaRepository : BaseFirebaseRepository() {
    @Suppress("PrivatePropertyName")
    private val TAG = "PeriksaRepository"

    val livePeriksaList = MutableLiveData<PeriksaListViewState>().apply {
        value = PeriksaListViewState()
    }

    val livePeriksa = MutableLiveData<PeriksaViewState>().apply {
        value = PeriksaViewState()
    }

    fun fetchPeriksaList() {

        livePeriksaList.value = PeriksaListViewState(loading = true)

        dbPeriksa.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                livePeriksaList.value = livePeriksaList.value?.copy(loading = false, error = error.toException())
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

                livePeriksaList.value = livePeriksaList.value?.copy(data = periksas, loading = false, isSucces = true)
            }

        })
    }

    fun fetchPeriksa(dokter: DokterModel) {

        livePeriksa.value = PeriksaViewState(loading = true)

        dbPeriksa.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                livePeriksa.value = livePeriksa.value?.copy(error = error.toException(), loading = false)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                var viewState = livePeriksa.value?.copy(loading = true)

                for (pasienSnapshots in snapshot.children) {
                    val periksa = pasienSnapshots.getValue(PeriksaModel::class.java)
                            ?: PeriksaModel()

                    if (periksa.dokter?.id == dokter.id) {
                        periksa.id = pasienSnapshots.key

                        viewState = viewState?.copy(isSucces = true, data = periksa)
                    }
                }

                livePeriksa.value = viewState

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