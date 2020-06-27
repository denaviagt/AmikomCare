package com.pengdst.amikomcare.datas.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.constants.RESULT_ERROR
import com.pengdst.amikomcare.datas.constants.RESULT_LOADING
import com.pengdst.amikomcare.datas.constants.RESULT_SUCCESS
import com.pengdst.amikomcare.datas.models.MahasiswaModel

@Suppress("unused")
class MahasiswaRepository : BaseMainRepository() {

    fun fetchMahasiswaList(){
        loadingList()
        dbMahasiswa.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveMahasiswaList.value = liveMahasiswaList.value?.copy(message = error.message, status = RESULT_ERROR)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val mahasiswas = mutableListOf<MahasiswaModel>()

                for (mahasiswaSnapshots in snapshot.children){
                    val mahasiswa = mahasiswaSnapshots.getValue(MahasiswaModel::class.java)

                    mahasiswa?.id = mahasiswaSnapshots.key
                    mahasiswa?.let {
                        mahasiswas.add(it)
                    }
                }

                liveMahasiswaList.value = liveMahasiswaList.value?.copy(data = mahasiswas, status = RESULT_SUCCESS)
            }

        })
    }

    private fun loadingList() {
        liveMahasiswaList.value = liveMahasiswaList.value?.copy(status = RESULT_LOADING)
    }

}