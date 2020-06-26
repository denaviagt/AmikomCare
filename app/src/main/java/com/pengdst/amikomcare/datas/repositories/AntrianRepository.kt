package com.pengdst.amikomcare.datas.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.AntrianModel
import com.pengdst.amikomcare.ui.viewstates.AntrianListViewState

class AntrianRepository : BaseFirebaseRepository() {
    private val dbAntrian = dbData.child(NODE_ANTRIAN)

    private val liveDataAntrian = MutableLiveData<AntrianListViewState>()

    fun fetchAntrianList(){

        liveDataAntrian.value = AntrianListViewState()

        dbAntrian.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveDataAntrian.value = liveDataAntrian.value?.copy(loading = false, isSucces = false, error = error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val antrians = mutableListOf<AntrianModel>()

                for (antrianSnapshot in snapshot.children){
                    val antrian = antrianSnapshot.getValue(AntrianModel::class.java)

                    antrian?.noAntrian = antrianSnapshot.key?.toInt()
                    antrian?.let {
                        antrians.add(it)
                    }
                }

                liveDataAntrian.value = liveDataAntrian.value?.copy(data = antrians, loading = false, isSucces = true)
            }

        })
    }

    // FIXME: 26/06/2020 Delete This
    fun observeAntrianList(): LiveData<AntrianListViewState> {
        return liveDataAntrian
    }
}