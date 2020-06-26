package com.pengdst.amikomcare.datas.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.constants.RESULT_ERROR
import com.pengdst.amikomcare.datas.constants.RESULT_SUCCESS
import com.pengdst.amikomcare.datas.models.DokterModel

class DokterMainRepository : BaseMainRepository() {


    fun updateDokter(dokter: DokterModel){

        dbDokter.child(dokter.id!!).setValue(dokter)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        liveDokter.value = liveDokter.value?.copy(data = dokter, status = RESULT_SUCCESS)
                    }
                    else{
                        liveDokter.value = liveDokter.value?.copy(message = it.exception?.message)
                    }
                }
    }

    fun fetchDokter(email: String, password: String) {

        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveDokter.value = liveDokter.value?.copy(status = RESULT_ERROR, message = error.toException().message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if ((dokter?.email?.equals(email) == true) && (dokter.password.equals(password))) {
                        dokter.id = dokterSnapshots.key

                        liveDokter.value = liveDokter.value?.copy(data = dokter, status = RESULT_SUCCESS, message = "Sukses Login dengan Email: ${dokter.email}")
                    }
                }
            }

        })
    }

    fun fetchDokter(email: String) {
        dbDokter.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveDokter.value = liveDokter.value?.copy(status = RESULT_ERROR, message = error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                for (dokterSnapshots in snapshot.children) {
                    val dokter = dokterSnapshots.getValue(DokterModel::class.java)

                    if (dokter?.email?.equals(email) == true) {
                        dokter.id = dokterSnapshots.key

                        liveDokter.value = liveDokter.value?.copy(data = dokter, status = RESULT_SUCCESS, message = "Sukses Login dengan Email Google: ${dokter.email}")
                    }
                }

            }

        })
    }

}