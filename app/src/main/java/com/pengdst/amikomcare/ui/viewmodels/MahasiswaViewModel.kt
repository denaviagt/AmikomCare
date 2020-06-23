package com.pengdst.amikomcare.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pengdst.amikomcare.datas.models.AntrianModel
import com.pengdst.amikomcare.datas.models.DokterModel
import com.pengdst.amikomcare.datas.models.MahasiswaModel
import com.pengdst.amikomcare.preferences.SessionUtil

class MahasiswaViewModel : ViewModel() {
    private val NODE_LOGIN = "login"
    private val NODE_MAHASISWA = "mahasiswa"
    private val NODE_DATA = "data"
    private val NODE_ANTRIAN = "antrian"

    private val liveDataAntrian = MutableLiveData<MutableList<AntrianModel>>()
    private val liveDataMahasiswa = MutableLiveData<MutableList<MahasiswaModel>>()

    lateinit var sessionUtil: SessionUtil
    lateinit var dokter: DokterModel

    private val db = FirebaseDatabase.getInstance().getReference(NODE_DATA)

    fun fetchMahasiswa(){
        db.child(NODE_MAHASISWA).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

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

                liveDataMahasiswa.value = mahasiswas
            }

        })
    }

    fun fetchAntrian(){
        db.child(NODE_ANTRIAN).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val mahasiswas = mutableListOf<AntrianModel>()

                for (mahasiswaSnapshots in snapshot.children){
                    val mahasiswa = mahasiswaSnapshots.getValue(AntrianModel::class.java)

                    mahasiswa?.noAntrian = mahasiswaSnapshots.key?.toInt()
                    mahasiswa?.let {
                        mahasiswas.add(it)
                    }
                }

                liveDataAntrian.value = mahasiswas
            }

        })
    }

    fun observeAntrian(): LiveData<MutableList<AntrianModel>>{
        return liveDataAntrian
    }
}
