package com.pengdst.amikomcare.datas.daos

import android.util.Log
import com.pengdst.amikomcare.datas.models.PasienModel

class PasienDAO {
    val TAG = "PasienDAO"
    var pasiens = mutableListOf<PasienModel>()

    fun select(): MutableList<PasienModel> {
        return pasiens
    }

    fun select(pasienId: String): PasienModel {
        var pasienModel = PasienModel()
        pasiens.find {
            val found = false
            if (it.id == pasienId) {
                pasienModel = it
            }
            return@find found
        }

        return pasienModel
    }

    fun insert(pasien: PasienModel) {
        pasiens.add(pasien)
        Log.e(TAG, "insert: ${pasiens.size}")
    }

    fun update(pasien: PasienModel) {
        pasiens.find {
            if (it.id == pasien.id) {
                val index = pasiens.indexOf(it)
                pasiens[index] = pasien
            }

            return@find pasien == it
        }

        Log.e(TAG, "update: ${pasiens.size}")
    }

    fun delete(pasien: PasienModel) {
        pasiens.remove(pasien)
        Log.e(TAG, "delete: ${pasiens.size}")
    }

    fun isInsertable(pasien: PasienModel): Boolean {

        if (pasiens.isNotEmpty()) {
            pasiens.forEach {
                if (it.id == pasien.id){
                    return false
                }
            }
        }

        return true
    }

    fun replace(pasien: PasienModel) {
        var found = false

        if (isInsertable(pasien)) {
            insert(pasien)
        } else {
            update(pasien)
        }
    }

    fun replaceAll(newPasiens: MutableList<PasienModel>?) {
        if (newPasiens != null) {
            pasiens = newPasiens
        }
        Log.e(TAG, "replaceAll: ${pasiens.size}")
    }

}