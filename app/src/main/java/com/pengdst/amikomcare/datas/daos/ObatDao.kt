package com.pengdst.amikomcare.datas.daos

import android.util.Log
import com.pengdst.amikomcare.datas.models.ObatModel

@Suppress("unused")
class ObatDao {

    @Suppress("SpellCheckingInspection", "PropertyName")
    val TAG = "ObatDao"

    @Suppress("SpellCheckingInspection")
    private var obats = mutableListOf<ObatModel?>()

    fun select(): MutableList<ObatModel?> {
        obats.sortBy {
            it?.id
        }
        return obats
    }

    fun select(pasienId: String): ObatModel {
        var pasienModel = ObatModel()
        obats.find {
            val found = false
            if (it?.id == pasienId) {
                pasienModel = it
            }
            return@find found
        }

        return pasienModel
    }

    private fun insert(pasien: ObatModel) {
        obats.add(pasien)
        Log.d(TAG, "insert: ${obats.size}")
    }

    private fun update(pasien: ObatModel) {
        obats.find {
            if (it?.id == pasien.id) {
                val index = obats.indexOf(it)
                obats[index] = pasien
            }

            return@find pasien == it
        }

        Log.d(TAG, "update: ${obats.size}")
    }

    fun delete(pasien: ObatModel) {
        obats.remove(pasien)
        Log.d(TAG, "delete: ${obats.size}")
    }

    private fun checkInsertable(pasien: ObatModel): Boolean {

        if (obats.isNotEmpty()) {
            obats.forEach {
                if (it?.id == pasien.id) {
                    return false
                }
            }
        }

        return true
    }

    fun replace(pasien: ObatModel) {
        var found = false

        if (checkInsertable(pasien)) {
            insert(pasien)
        } else {
            update(pasien)
        }
    }

    fun replaceAll(newPasiens: MutableList<ObatModel>?) {
        if (newPasiens != null) {
            obats = newPasiens.toMutableList()
        }
    }

}