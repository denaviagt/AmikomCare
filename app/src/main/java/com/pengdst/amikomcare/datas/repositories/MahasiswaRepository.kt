package com.pengdst.amikomcare.datas.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.MahasiswaModel
import com.pengdst.amikomcare.datas.viewstates.MahasiswaListViewState

@Suppress("unused")
class MahasiswaRepository : BaseFirebaseRepository() {

    private val dbMahasiswa = dbData.child(NODE_MAHASISWA)

    val liveMahasiswaList = MutableLiveData<MahasiswaListViewState>()

    fun fetchMahasiswaList(){

        liveMahasiswaList.value = MahasiswaListViewState(loading = true)

        dbMahasiswa.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                liveMahasiswaList.value = liveMahasiswaList.value?.copy(error = error.toException(), isSucces = false, loading = false)
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

                liveMahasiswaList.value = liveMahasiswaList.value?.copy(data = mahasiswas, isSucces = true, loading = false)
            }

        })
    }

}