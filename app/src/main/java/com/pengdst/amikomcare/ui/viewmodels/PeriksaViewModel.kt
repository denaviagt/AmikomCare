package com.pengdst.amikomcare.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.PeriksaModel

class PeriksaViewModel : BaseFirebaseViewModel(){

    val TAG = "PeriksaViewModel"

    val liveDataPeriksa = MutableLiveData<MutableList<PeriksaModel>>()

    fun fetch(){
        dbPeriksa.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                val periksas = mutableListOf<PeriksaModel>()

                for (dokterSnapshots in snapshot.children) {

                    val periksa = dokterSnapshots.getValue(PeriksaModel::class.java) ?: PeriksaModel()

                    periksa.id = dokterSnapshots.key

                    periksa.let {
                        periksas.add(it)
                    }

                    Log.e(TAG, periksa.toString())
                }

                liveDataPeriksa.value = periksas
            }

        })
    }

    fun add(periksa: PeriksaModel){
        dbPeriksa.child(periksa.id!!).setValue(periksa)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        Log.e(TAG, "add: ${it.result}")
                    }
                    else
                        Log.e(TAG, "add: ${it.exception}")
                }
    }

    fun observe(): LiveData<MutableList<PeriksaModel>> {
        return liveDataPeriksa
    }

}