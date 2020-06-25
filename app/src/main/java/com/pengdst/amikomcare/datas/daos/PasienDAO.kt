package com.pengdst.amikomcare.datas.daos

import android.util.Log
import com.pengdst.amikomcare.datas.models.PasienModel

class PasienDAO {
    val TAG = "PasienDAO"
    var pasiens = mutableListOf<PasienModel?>()

    fun select(): MutableList<PasienModel?> {
        pasiens.sortBy {
            it?.id
        }
        return pasiens
    }

    fun select(pasienId: String): PasienModel {
        var pasienModel = PasienModel()
        pasiens.find {
            val found = false
            if (it?.id == pasienId) {
                pasienModel = it
            }
            return@find found
        }

        return pasienModel
    }

    fun insert(pasien: PasienModel) {
        pasiens.add(pasien)
        Log.d(TAG, "insert: ${pasiens.size}")
    }

    fun update(pasien: PasienModel) {
        pasiens.find {
            if (it?.id == pasien.id) {
                val index = pasiens.indexOf(it)
                pasiens[index] = pasien
            }

            return@find pasien == it
        }

        Log.d(TAG, "update: ${pasiens.size}")
    }

    fun delete(pasien: PasienModel) {
        pasiens.remove(pasien)
        Log.d(TAG, "delete: ${pasiens.size}")
    }

    fun checkInsertable(pasien: PasienModel): Boolean {

        if (pasiens.isNotEmpty()) {
            pasiens.forEach {
                if (it?.id == pasien.id){
                    return false
                }
            }
        }

        return true
    }

    fun replace(pasien: PasienModel) {
        var found = false

        if (checkInsertable(pasien)) {
            insert(pasien)
        } else {
            update(pasien)
        }
    }

    fun replaceAll(newPasiens: MutableList<PasienModel?>) {
        pasiens = newPasiens
        Log.d(TAG, "replaceAll: ${pasiens.size}")
    }

}