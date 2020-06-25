package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.AntrianModel
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.datas.models.MahasiswaModel
import com.pengdst.amikomcare.ui.viewstates.AntrianListViewState
import com.pengdst.amikomcare.ui.viewstates.AntrianViewState
import com.pengdst.amikomcare.ui.viewstates.MahasiswaListViewState

class MahasiswaViewModel : BaseFirebaseViewModel() {
    val TAG = "MahasiswaViewModel"

    private val liveDataAntrian = MutableLiveData<AntrianListViewState>()
    private val liveDataMahasiswa = MutableLiveData<MahasiswaListViewState>()

    lateinit var dokter: DokterModel

    private val dbMahasiswa = dbData.child(NODE_MAHASISWA)
    private val dbAntrian = dbData.child(NODE_ANTRIAN)

    fun fetchMahasiswa(){

        liveDataMahasiswa.value = MahasiswaListViewState(loading = true)

        dbMahasiswa.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                liveDataMahasiswa.value = liveDataMahasiswa.value?.copy(error = error.toException(), isSucces = false, loading = false)
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

                liveDataMahasiswa.value = liveDataMahasiswa.value?.copy(data = mahasiswas, isSucces = true, loading = false)
            }

        })
    }

    fun fetchAntrian(){

        liveDataAntrian.value = AntrianListViewState()

        dbAntrian.addValueEventListener(object : ValueEventListener{
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

    fun observeAntrian(): LiveData<AntrianListViewState> {
        return liveDataAntrian
    }
}
