package com.pengdst.amikomcare.datas.repositories

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.constants.RESULT_ERROR
import com.pengdst.amikomcare.datas.constants.RESULT_LOADING
import com.pengdst.amikomcare.datas.constants.RESULT_SUCCESS
import com.pengdst.amikomcare.datas.models.AntrianModel

class AntrianRepository : BaseMainRepository() {

    @Suppress("PrivatePropertyName")
    private val TAG = "AntrianRepository"

    fun fetchAntrianList(){
        loadingList()
        dbAntrian.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveAntrianList.value = liveAntrianList.value?.copy(status = RESULT_ERROR, message = error.message)
                Log.e(TAG, "onCancelled() called with: error = $error")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val antrians = mutableListOf<AntrianModel>()

                for (antrianSnapshot in snapshot.children){

                    val antrian = antrianSnapshot.getValue(AntrianModel::class.java)

                    antrian?.id = antrianSnapshot.key
                    antrian?.let {
                        antrians.add(it)
                    }

                }

                liveAntrianList.value = liveAntrianList.value?.copy(data = antrians, status = RESULT_SUCCESS, message = "Success Load Data")
            }

        })
    }

    fun fetchAntrianList(idDokter: String){
        loadingList()
        dbAntrian.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveAntrianList.value = liveAntrianList.value?.copy(status = RESULT_ERROR, message = error.message)
                Log.e(TAG, "onCancelled() called with: error = $error")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val antrians = mutableListOf<AntrianModel>()

                for (antrianSnapshot in snapshot.children){

                    val antrian = antrianSnapshot.getValue(AntrianModel::class.java)

                    antrian?.id = antrianSnapshot.key

                    if (antrian?.dokter?.id == idDokter){
                        antrian.let {
                            antrians.add(it)
                        }
                    }

                }

                liveAntrianList.value = liveAntrianList.value?.copy(data = antrians, status = RESULT_SUCCESS, message = "Success Load Data")
            }

        })
    }

    fun removeAntrian(id: String?){
        if (!id.isNullOrEmpty()){
            dbAntrian.child(id).removeValue()
                    .addOnSuccessListener {
                        liveAntrianList.value = liveAntrianList.value?.copy(status = RESULT_SUCCESS, message = null)
                    }
                    .addOnFailureListener {
                        @Suppress("SpellCheckingInspection")
                        liveAntrianList.value = liveAntrianList.value?.copy(status = RESULT_ERROR, message = "Gagal Hapus Data")
                    }
        }
    }

    private fun loadingList() {
        liveAntrianList.value = liveAntrianList.value?.copy(status = RESULT_LOADING)
    }

}