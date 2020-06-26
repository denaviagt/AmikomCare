package com.pengdst.amikomcare.datas.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.AntrianModel
import com.pengdst.amikomcare.ui.viewstates.AntrianListViewState

class AntrianRepository : BaseFirebaseRepository() {
    private val dbAntrian = dbData.child(NODE_ANTRIAN)

    val liveAntrianList = MutableLiveData<AntrianListViewState>()

    fun fetchAntrianList(){

        liveAntrianList.value = AntrianListViewState()

        dbAntrian.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveAntrianList.value = liveAntrianList.value?.copy(loading = false, isSucces = false, error = error.toException())
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

                liveAntrianList.value = liveAntrianList.value?.copy(data = antrians, loading = false, isSucces = true)
            }

        })
    }

}